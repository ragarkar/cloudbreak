package com.sequenceiq.datalake.service.sdx;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.StackV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.cm.ClouderaManagerSyncV4Request;
import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.common.exception.WebApplicationExceptionMessageExtractor;
import com.sequenceiq.cloudbreak.exception.CloudbreakApiException;
import com.sequenceiq.datalake.entity.SdxCluster;
import com.sequenceiq.flow.api.model.FlowIdentifier;

@Service
public class SdxCmSyncService {

    @Inject
    private SdxService sdxService;

    @Inject
    private StackV4Endpoint stackV4Endpoint;

    @Inject
    private CloudbreakFlowService cloudbreakFlowService;

    @Inject
    private WebApplicationExceptionMessageExtractor exceptionMessageExtractor;

    public void callCmSync(Long sdxId) {
        SdxCluster sdxCluster = sdxService.getById(sdxId);
        try {
            String initiatorUserCrn = ThreadBasedUserCrnProvider.getUserCrn();
            ClouderaManagerSyncV4Request clouderaManagerSyncV4Request = new ClouderaManagerSyncV4Request().withCandidateImageUuids(Set.of());
            FlowIdentifier flowIdentifier = ThreadBasedUserCrnProvider.doAsInternalActor(() ->
                    stackV4Endpoint.syncCm(0L, sdxCluster.getClusterName(), initiatorUserCrn, clouderaManagerSyncV4Request));
            cloudbreakFlowService.saveLastCloudbreakFlowChainId(sdxCluster, flowIdentifier);
        } catch (WebApplicationException e) {
            String exceptionMessage = exceptionMessageExtractor.getErrorMessage(e);
            String message = String.format("Couldn't call cm sync for cluster: [%s]. Message: [%s]", sdxCluster.getClusterName(), exceptionMessage);
            throw new CloudbreakApiException(message, e);
        }
    }
}
