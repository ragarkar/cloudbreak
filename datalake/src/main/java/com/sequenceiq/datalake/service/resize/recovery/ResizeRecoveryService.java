package com.sequenceiq.datalake.service.resize.recovery;

import static com.sequenceiq.cloudbreak.common.exception.NotFoundException.notFound;
import static com.sequenceiq.datalake.entity.DatalakeStatusEnum.DELETE_FAILED;
import static com.sequenceiq.datalake.entity.DatalakeStatusEnum.PROVISIONING_FAILED;
import static com.sequenceiq.datalake.entity.DatalakeStatusEnum.RUNNING;
import static com.sequenceiq.datalake.entity.DatalakeStatusEnum.STOPPED;
import static com.sequenceiq.datalake.entity.DatalakeStatusEnum.STOP_FAILED;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.recovery.RecoveryStatus;
import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.auth.altus.EntitlementService;
import com.sequenceiq.cloudbreak.common.exception.BadRequestException;
import com.sequenceiq.datalake.entity.DatalakeStatusEnum;
import com.sequenceiq.datalake.entity.SdxCluster;
import com.sequenceiq.datalake.entity.SdxStatusEntity;
import com.sequenceiq.datalake.flow.SdxReactorFlowManager;
import com.sequenceiq.datalake.repository.SdxClusterRepository;
import com.sequenceiq.datalake.service.recovery.RecoveryService;
import com.sequenceiq.datalake.service.sdx.status.SdxStatusService;
import com.sequenceiq.sdx.api.model.SdxRecoverableResponse;
import com.sequenceiq.sdx.api.model.SdxRecoveryRequest;
import com.sequenceiq.sdx.api.model.SdxRecoveryResponse;

@Component
/**
 * Provides entrypoint for recovery of failed SDX resize.
 *
 * The main entry point is {@code triggerRecovery}, which starts a cloudbreak Flow to recover the Data Lake.
 * ensure a Resize recovery is appropriate using {@code validateRecovery}
 *
 */
public class ResizeRecoveryService implements RecoveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResizeRecoveryService.class);

    @Inject
    private EntitlementService entitlementService;

    @Inject
    private SdxStatusService sdxStatusService;

    @Inject
    private SdxReactorFlowManager sdxReactorFlowManager;

    @Inject
    private SdxClusterRepository sdxClusterRepository;

    @Override
    public SdxRecoverableResponse validateRecovery(SdxCluster sdxCluster, SdxRecoveryRequest request) {
        if (!entitlementService.isDatalakeResizeRecoveryEnabled(ThreadBasedUserCrnProvider.getAccountId())) {
            return new SdxRecoverableResponse("Resize Recovery entitlement not enabled", RecoveryStatus.NON_RECOVERABLE);
        }
        SdxStatusEntity actualStatusForSdx = sdxStatusService.getActualStatusForSdx(sdxCluster);
        DatalakeStatusEnum status = actualStatusForSdx.getStatus();
        String statusReason = actualStatusForSdx.getStatusReason();

        if (getOldCluster(sdxCluster).isPresent()) {
            return validateRecoveryResizedClusterPresent(sdxCluster, status, statusReason);
        }
        return validateRecoveryOnlyOriginalCluster(sdxCluster, status, statusReason);
    }

    @Override
    public SdxRecoverableResponse validateRecovery(SdxCluster sdxCluster) {
        return validateRecovery(sdxCluster, null);
    }

    @Override
    public SdxRecoveryResponse triggerRecovery(SdxCluster sdxCluster, SdxRecoveryRequest sdxRecoveryRequest) {
        SdxStatusEntity actualStatusForSdx = sdxStatusService.getActualStatusForSdx(sdxCluster);
        if (entitlementService.isDatalakeResizeRecoveryEnabled(ThreadBasedUserCrnProvider.getAccountId())) {
            switch (actualStatusForSdx.getStatus()) {
                case STOP_FAILED:
                    return new SdxRecoveryResponse(sdxReactorFlowManager.triggerSdxStartFlow(sdxCluster));
                case STOPPED:
                    return new SdxRecoveryResponse(sdxReactorFlowManager.triggerSdxResizeRecovery(sdxCluster, null));
                case PROVISIONING_FAILED:
                    return new SdxRecoveryResponse(sdxReactorFlowManager.triggerSdxResizeRecovery(getOldClusterErrorIfNotFound(sdxCluster), sdxCluster));
                case RUNNING:
                    if (actualStatusForSdx.getStatusReason().contains("Datalake restore failed")) {
                        return new SdxRecoveryResponse(sdxReactorFlowManager.triggerSdxResizeRecovery(getOldClusterErrorIfNotFound(sdxCluster), sdxCluster));
                    } else {
                        throw new NotImplementedException("Cluster is currently running and cannot be recovered");
                    }
                default:
                    throw new NotImplementedException("Cluster is currently in an unrecoverable state");
            }
        } else {
            throw new BadRequestException("Entitlement for resize recovery is missing");
        }
    }

    private SdxCluster getOldClusterErrorIfNotFound(SdxCluster newCluster) {
        return getOldCluster(newCluster).orElseThrow(notFound(
                "detached SDX cluster",
                "Env CRN: " + newCluster.getEnvCrn() + ", Account ID: " + newCluster.getAccountId()
        ));
    }

    private Optional<SdxCluster> getOldCluster(SdxCluster newCluster) {
        Optional<SdxCluster> oldCluster = sdxClusterRepository.findByAccountIdAndEnvCrnAndDeletedIsNullAndDetachedIsTrue(
                newCluster.getAccountId(), newCluster.getEnvCrn()
        );
        return (oldCluster.isPresent() && !oldCluster.get().getClusterName().equals(newCluster.getClusterName())) ?
                oldCluster : Optional.empty();
    }

    private String getReasonNonRecoverable(DatalakeStatusEnum status) {
        String reason = "";
        if (RUNNING.equals(status)) {
            reason = "Datalake is running, resize can not be recovered from this point";
        } else if (DELETE_FAILED.equals(status)) {
            reason = "Failed to delete original data lake, not a recoverable error";
        }
        return reason.isEmpty() ? reason : (": " + reason);
    }

    private SdxRecoverableResponse validateRecoveryResizedClusterPresent(SdxCluster sdxCluster, DatalakeStatusEnum status, String statusReason) {
        if (PROVISIONING_FAILED.equals(status)) {
            return new SdxRecoverableResponse("Failed to provision, recovery will restart original data lake, and delete the new one",
                    RecoveryStatus.RECOVERABLE);
        } else if (RUNNING.equals(status) && statusReason.contains("Datalake restore failed")) {
            return new SdxRecoverableResponse(
                    "Failed to restore backup to new data lake, recovery will restart original data lake, and delete the new one",
                    RecoveryStatus.RECOVERABLE
            );
        }
        return new SdxRecoverableResponse(
                "Resize can not be recovered from this point" + getReasonNonRecoverable(status),
                RecoveryStatus.NON_RECOVERABLE
        );
    }

    private SdxRecoverableResponse validateRecoveryOnlyOriginalCluster(SdxCluster sdxCluster, DatalakeStatusEnum status, String statusReason) {
        if (STOPPED.equals(status)) {
            if (sdxCluster.isDetached()) {
                return new SdxRecoverableResponse("Resize can recover detached cluster", RecoveryStatus.RECOVERABLE);
            } else if (statusReason.contains("SDX detach failed")) {
                return new SdxRecoverableResponse("Resize can recover stopped cluster", RecoveryStatus.RECOVERABLE);
            }
        } else if (STOP_FAILED.equals(status) && statusReason.contains("Datalake resize failure")) {
            return new SdxRecoverableResponse("Resize can be recovered from a failed stop", RecoveryStatus.RECOVERABLE);
        }
        return new SdxRecoverableResponse(
                "Resize can not be recovered from this point" + getReasonNonRecoverable(status),
                RecoveryStatus.NON_RECOVERABLE
        );
    }
}
