package com.sequenceiq.datalake.service.loadbalancer.dns;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.StackV4Endpoint;
import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.auth.crn.RegionAwareInternalCrnGeneratorFactory;
import com.sequenceiq.datalake.entity.SdxCluster;

@Service
public class UpdateLoadBalancerDNSService {
    @Inject
    private StackV4Endpoint stackV4Endpoint;

    @Inject
    private RegionAwareInternalCrnGeneratorFactory regionAwareInternalCrnGeneratorFactory;

    public void performLoadBalancerDNSUpdate(SdxCluster sdxCluster) {
        String initiatorUserCrn = ThreadBasedUserCrnProvider.getUserCrn();
        ThreadBasedUserCrnProvider.doAsInternalActor(
                regionAwareInternalCrnGeneratorFactory.iam().getInternalCrnForServiceAsString(),
                () -> stackV4Endpoint.updateLoadBalancerDNS(0L, sdxCluster.getClusterName(), initiatorUserCrn)
        );
    }
}
