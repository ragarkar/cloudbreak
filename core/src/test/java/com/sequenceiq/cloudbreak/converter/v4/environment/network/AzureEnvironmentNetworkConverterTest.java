package com.sequenceiq.cloudbreak.converter.v4.environment.network;

import static com.sequenceiq.cloudbreak.common.network.NetworkConstants.CLOUD_PLATFORM;
import static com.sequenceiq.cloudbreak.common.network.NetworkConstants.ENDPOINT_GATEWAY_SUBNET_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import com.sequenceiq.cloudbreak.cloud.model.CloudSubnet;
import com.sequenceiq.cloudbreak.common.converter.MissingResourceNameGenerator;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.domain.Network;
import com.sequenceiq.common.api.type.PublicEndpointAccessGateway;
import com.sequenceiq.environment.api.v1.environment.model.EnvironmentNetworkAzureParams;
import com.sequenceiq.environment.api.v1.environment.model.response.EnvironmentNetworkResponse;

@MockitoSettings
class AzureEnvironmentNetworkConverterTest {

    private static final String DATABASE_PRIVATE_DNS_ZONE_ID = "my_database_private_dns_zone_id";

    private static final String DATABASE_PRIVATE_DNS_ZONE_ID_KEY = "databasePrivateDsZoneId";

    private static final String NETWORK_ID = "my_network_id";

    private static final String NETWORK_ID_KEY = "networkId";

    private static final String RESOURCE_GROUP_NAME = "my_resource_group";

    private static final String RESOURCE_GROUP_NAME_KEY = "resourceGroupName";

    private static final String NO_PUBLIC_IP_KEY = "noPublicIp";

    @Mock
    private MissingResourceNameGenerator missingResourceNameGenerator;

    @Mock
    private SubnetSelector subnetSelector;

    @InjectMocks
    private AzureEnvironmentNetworkConverter converter;

    @Test
    void testConvertToLegacyNetworkWithPublicEndpointAccessGatewayEnabled() {
        EnvironmentNetworkResponse environmentNetworkResponse = mock(EnvironmentNetworkResponse.class);
        EnvironmentNetworkAzureParams azure = mock(EnvironmentNetworkAzureParams.class);
        CloudSubnet cloudSubnet = mock(CloudSubnet.class);
        when(environmentNetworkResponse.getPublicEndpointAccessGateway()).thenReturn(PublicEndpointAccessGateway.ENABLED);
        when(subnetSelector.chooseSubnet(any(), any(), any(), any())).thenReturn(Optional.of(cloudSubnet));

        when(cloudSubnet.getId()).thenReturn("my_cloud_subnet");

        when(environmentNetworkResponse.getAzure()).thenReturn(azure);
        when(azure.getNetworkId()).thenReturn(NETWORK_ID);
        when(azure.getResourceGroupName()).thenReturn(RESOURCE_GROUP_NAME);
        when(azure.getNoPublicIp()).thenReturn(true);

        Network result = converter.convertToLegacyNetwork(environmentNetworkResponse, "my-az");

        assertEquals(CloudPlatform.AZURE.toString(), result.getAttributes().getMap().get(CLOUD_PLATFORM).toString());
        assertFalse(result.getAttributes().getMap().containsKey(ENDPOINT_GATEWAY_SUBNET_ID));
    }

    @Test
    void testGetAttributesForLegacyNetwork() {
        EnvironmentNetworkResponse environmentNetworkResponse = mock(EnvironmentNetworkResponse.class);
        EnvironmentNetworkAzureParams azure = mock(EnvironmentNetworkAzureParams.class);
        when(environmentNetworkResponse.getAzure()).thenReturn(azure);
        when(azure.getNetworkId()).thenReturn(NETWORK_ID);
        when(azure.getResourceGroupName()).thenReturn(RESOURCE_GROUP_NAME);
        when(azure.getNoPublicIp()).thenReturn(true);
        when(azure.getDatabasePrivateDnsZoneId()).thenReturn(DATABASE_PRIVATE_DNS_ZONE_ID);

        Map<String, Object> result = converter.getAttributesForLegacyNetwork(environmentNetworkResponse);

        assertThat(result).hasSize(4);
        assertEquals(NETWORK_ID, result.get(NETWORK_ID_KEY));
        assertEquals(RESOURCE_GROUP_NAME, result.get(RESOURCE_GROUP_NAME_KEY));
        assertEquals(true, result.get(NO_PUBLIC_IP_KEY));
        assertEquals(DATABASE_PRIVATE_DNS_ZONE_ID, result.get(DATABASE_PRIVATE_DNS_ZONE_ID_KEY));
    }

    @Test
    void testGetCloudPlatform() {
        assertEquals(CloudPlatform.AZURE, converter.getCloudPlatform());
    }
}
