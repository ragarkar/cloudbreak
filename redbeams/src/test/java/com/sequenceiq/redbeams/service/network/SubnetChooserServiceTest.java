package com.sequenceiq.redbeams.service.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sequenceiq.cloudbreak.cloud.CloudConnector;
import com.sequenceiq.cloudbreak.cloud.NetworkConnector;
import com.sequenceiq.cloudbreak.cloud.init.CloudPlatformConnectors;
import com.sequenceiq.cloudbreak.cloud.model.CloudPlatformVariant;
import com.sequenceiq.cloudbreak.cloud.model.CloudSubnet;
import com.sequenceiq.cloudbreak.cloud.model.SubnetSelectionParameters;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.redbeams.domain.stack.DBStack;

@RunWith(MockitoJUnitRunner.class)
public class SubnetChooserServiceTest {

    private static final String AVAILABILITY_ZONE_A = "AZ-a";

    private static final String AVAILABILITY_ZONE_B = "AZ-b";

    private static final String SUBNET_1 = "subnet-1";

    private static final String SUBNET_2 = "subnet-2";

    private static final String SUBNET_3 = "subnet-3";

    @Mock
    private CloudPlatformConnectors cloudPlatformConnectors;

    @InjectMocks
    private  SubnetChooserService underTest;

    @Test
    public void testChooseSubnetsThenCloudPlatformConnectorGetIsCalled() {
        List<CloudSubnet> subnets = List.of();
        setupConnector();
        DBStack dbStack = new DBStack();
        dbStack.setCloudPlatform("MyCloudPlatform");
        dbStack.setPlatformVariant("MyPlatformVariant");

        underTest.chooseSubnets(subnets, CloudPlatform.AWS, dbStack);

        ArgumentCaptor<CloudPlatformVariant> cloudPlatformVariantArgumentCaptor = ArgumentCaptor.forClass(CloudPlatformVariant.class);
        verify(cloudPlatformConnectors).get(cloudPlatformVariantArgumentCaptor.capture());
        assertEquals("MyCloudPlatform", cloudPlatformVariantArgumentCaptor.getValue().getPlatform().value());
        assertEquals("MyPlatformVariant", cloudPlatformVariantArgumentCaptor.getValue().getVariant().value());
    }

    @Test
    public void testChooseSubnetsThenNetworkSelectorCalled() {
        List<CloudSubnet> subnets = List.of(
                new CloudSubnet(SUBNET_1, "", AVAILABILITY_ZONE_A, ""),
                new CloudSubnet(SUBNET_2, "", AVAILABILITY_ZONE_B, ""),
                new CloudSubnet(SUBNET_3, "", AVAILABILITY_ZONE_B, "")
        );
        NetworkConnector networkConnector = setupConnector();

        underTest.chooseSubnets(subnets, CloudPlatform.AWS, new DBStack());

        ArgumentCaptor<SubnetSelectionParameters> subnetSelectionParametersCaptor = ArgumentCaptor.forClass(SubnetSelectionParameters.class);
        verify(networkConnector).selectSubnets(eq(subnets), subnetSelectionParametersCaptor.capture());
        assertTrue(subnetSelectionParametersCaptor.getValue().isPreferPrivateNetwork());
        assertTrue(subnetSelectionParametersCaptor.getValue().isHa());
    }

    @Test
    public void testChooseSubnetsWhenAwsNoHaThenHaIsFalse() {
        List<CloudSubnet> subnets = List.of();
        NetworkConnector networkConnector = setupConnector();
        DBStack dbStack = mock(DBStack.class);
        when(dbStack.isHa()).thenReturn(false);

        underTest.chooseSubnets(subnets, CloudPlatform.AWS, dbStack);

        ArgumentCaptor<SubnetSelectionParameters> subnetSelectionParametersCaptor = ArgumentCaptor.forClass(SubnetSelectionParameters.class);
        verify(networkConnector).selectSubnets(eq(subnets), subnetSelectionParametersCaptor.capture());
        assertTrue(subnetSelectionParametersCaptor.getValue().isPreferPrivateNetwork());
        assertFalse(subnetSelectionParametersCaptor.getValue().isHa());
    }

    @Test
    public void testChooseSubnetsWhenAwsIsHaThenHaIsTrue() {
        List<CloudSubnet> subnets = List.of();
        NetworkConnector networkConnector = setupConnector();
        DBStack dbStack = mock(DBStack.class);
        when(dbStack.isHa()).thenReturn(true);

        underTest.chooseSubnets(subnets, CloudPlatform.AWS, dbStack);

        ArgumentCaptor<SubnetSelectionParameters> subnetSelectionParametersCaptor = ArgumentCaptor.forClass(SubnetSelectionParameters.class);
        verify(networkConnector).selectSubnets(eq(subnets), subnetSelectionParametersCaptor.capture());
        assertTrue(subnetSelectionParametersCaptor.getValue().isPreferPrivateNetwork());
        assertTrue(subnetSelectionParametersCaptor.getValue().isHa());
    }

    private NetworkConnector setupConnector() {
        CloudConnector cloudConnector = mock(CloudConnector.class);
        NetworkConnector networkConnector = mock(NetworkConnector.class);
        when(cloudConnector.networkConnector()).thenReturn(networkConnector);
        when(cloudPlatformConnectors.get(any())).thenReturn(cloudConnector);
        return networkConnector;
    }
}
