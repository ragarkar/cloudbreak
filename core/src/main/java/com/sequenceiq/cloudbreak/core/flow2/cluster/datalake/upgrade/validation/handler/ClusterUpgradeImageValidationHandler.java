package com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.handler;

import static com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.event.ClusterUpgradeValidationHandlerSelectors.VALIDATE_IMAGE_EVENT;
import static com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.event.ClusterUpgradeValidationStateSelectors.START_CLUSTER_UPGRADE_DISK_SPACE_VALIDATION_EVENT;

import java.util.HashSet;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.CloudConnector;
import com.sequenceiq.cloudbreak.cloud.Validator;
import com.sequenceiq.cloudbreak.cloud.ValidatorType;
import com.sequenceiq.cloudbreak.cloud.context.AuthenticatedContext;
import com.sequenceiq.cloudbreak.cloud.context.CloudContext;
import com.sequenceiq.cloudbreak.cloud.exception.CloudPlatformValidationWarningException;
import com.sequenceiq.cloudbreak.cloud.init.CloudPlatformConnectors;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.ClusterUpgradeImageValidationEvent;
import com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.event.ClusterUpgradeImageValidationFinishedEvent;
import com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.upgrade.validation.event.ClusterUpgradeValidationFailureEvent;
import com.sequenceiq.cloudbreak.eventbus.Event;
import com.sequenceiq.cloudbreak.service.parcel.ParcelAvailabilityService;
import com.sequenceiq.cloudbreak.service.upgrade.validation.ParcelSizeService;
import com.sequenceiq.flow.reactor.api.handler.ExceptionCatcherEventHandler;
import com.sequenceiq.flow.reactor.api.handler.HandlerEvent;

@Component
public class ClusterUpgradeImageValidationHandler extends ExceptionCatcherEventHandler<ClusterUpgradeImageValidationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterUpgradeImageValidationHandler.class);

    @Inject
    private CloudPlatformConnectors cloudPlatformConnectors;

    @Inject
    private ParcelAvailabilityService parcelAvailabilityService;

    @Inject
    private ParcelSizeService parcelSizeService;

    @Override
    protected Selectable doAccept(HandlerEvent<ClusterUpgradeImageValidationEvent> event) {
        LOGGER.debug("Accepting cluster upgrade image validation event.");
        ClusterUpgradeImageValidationEvent request = event.getData();
        CloudContext cloudContext = request.getCloudContext();
        try {
            Set<Response> parcelsResponses = parcelAvailabilityService.validateAvailability(request.getTargetImage(), request.getResourceId());
            long requiredDiskSpaceForUpgrade = parcelSizeService.getRequiredFreeSpace(parcelsResponses);
            Set<String> warningMessages = executePlatformSpecificValidations(request, cloudContext);
            LOGGER.debug("Cluster upgrade image validation succeeded.");
            return new ClusterUpgradeImageValidationFinishedEvent(START_CLUSTER_UPGRADE_DISK_SPACE_VALIDATION_EVENT.selector(), request.getResourceId(),
                    requiredDiskSpaceForUpgrade, warningMessages);
        } catch (RuntimeException e) {
            LOGGER.warn("Cluster upgrade image validation failed: ", e);
            return new ClusterUpgradeValidationFailureEvent(request.getResourceId(), e);
        }
    }

    private Set<String> executePlatformSpecificValidations(ClusterUpgradeImageValidationEvent request, CloudContext cloudContext) {
        Set<String> warningMessages = new HashSet<>();
        CloudConnector connector = cloudPlatformConnectors.get(cloudContext.getPlatformVariant());
        AuthenticatedContext ac = connector.authentication().authenticate(cloudContext, request.getCloudCredential());
        for (Validator validator : connector.validators(ValidatorType.IMAGE)) {
            try {
                validator.validate(ac, request.getCloudStack());
            } catch (CloudPlatformValidationWarningException e) {
                LOGGER.warn("{} sent the following warning: {}", validator.getClass().getSimpleName(), e.getMessage());
                warningMessages.add(e.getMessage());
            }
        }
        return warningMessages;
    }

    @Override
    public String selector() {
        return VALIDATE_IMAGE_EVENT.selector();
    }

    @Override
    protected Selectable defaultFailureEvent(Long resourceId, Exception e, Event<ClusterUpgradeImageValidationEvent> event) {
        LOGGER.error("Cluster upgrade image validation failed: ", e);
        return new ClusterUpgradeValidationFailureEvent(resourceId, e);
    }

}
