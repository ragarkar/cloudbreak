package com.sequenceiq.environment.network.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.init.CloudPlatformConnectors;
import com.sequenceiq.cloudbreak.cloud.model.CloudPlatformVariant;
import com.sequenceiq.cloudbreak.cloud.model.CloudSubnet;
import com.sequenceiq.cloudbreak.cloud.model.SubnetSelectionParameters;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.common.api.type.Tunnel;
import com.sequenceiq.environment.network.dto.NetworkDto;

@Component
public class SubnetIdProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubnetIdProvider.class);

    @Inject
    private CloudPlatformConnectors cloudPlatformConnectors;

    public String provide(NetworkDto network, Tunnel tunnel, CloudPlatform cloudPlatform) {
        LOGGER.debug("Choosing subnet, network: {},  platform: {}, tunnel: {}", network, cloudPlatform, tunnel);
        if (network == null || network.getSubnetIds() == null || network.getSubnetIds().isEmpty() || network.getSubnetMetas().isEmpty()) {
            LOGGER.debug("Check failed, returning null");
            return null;
        }
        List<CloudSubnet> selectedSubnets = cloudPlatformConnectors.get(new CloudPlatformVariant(cloudPlatform.name(), cloudPlatform.name()))
                .networkConnector()
                .selectSubnets(new ArrayList<>(network.getSubnetMetas().values()), SubnetSelectionParameters.builder().withTunnel(tunnel).build());
        return selectedSubnets.get(0).getId();
    }
}
