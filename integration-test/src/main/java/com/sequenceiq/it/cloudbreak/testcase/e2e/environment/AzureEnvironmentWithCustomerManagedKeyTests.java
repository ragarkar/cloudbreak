package com.sequenceiq.it.cloudbreak.testcase.e2e.environment;

import static com.sequenceiq.it.cloudbreak.cloud.HostGroupType.MASTER;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.common.api.type.Tunnel;
import com.sequenceiq.environment.api.v1.environment.model.response.DetailedEnvironmentResponse;
import com.sequenceiq.environment.api.v1.environment.model.response.EnvironmentStatus;
import com.sequenceiq.freeipa.api.v1.freeipa.stack.model.common.Status;
import com.sequenceiq.freeipa.api.v1.operation.model.OperationState;
import com.sequenceiq.it.cloudbreak.EnvironmentClient;
import com.sequenceiq.it.cloudbreak.FreeIpaClient;
import com.sequenceiq.it.cloudbreak.client.CredentialTestClient;
import com.sequenceiq.it.cloudbreak.client.EnvironmentTestClient;
import com.sequenceiq.it.cloudbreak.client.FreeIpaTestClient;
import com.sequenceiq.it.cloudbreak.cloud.v4.CommonCloudProperties;
import com.sequenceiq.it.cloudbreak.cloud.v4.azure.AzureCloudProvider;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.credential.CredentialTestDto;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentNetworkTestDto;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentTestDto;
import com.sequenceiq.it.cloudbreak.dto.freeipa.FreeIpaTestDto;
import com.sequenceiq.it.cloudbreak.dto.freeipa.FreeIpaUserSyncTestDto;
import com.sequenceiq.it.cloudbreak.dto.telemetry.TelemetryTestDto;
import com.sequenceiq.it.cloudbreak.exception.TestFailException;
import com.sequenceiq.it.cloudbreak.log.Log;
import com.sequenceiq.it.cloudbreak.testcase.e2e.AbstractE2ETest;
import com.sequenceiq.it.cloudbreak.util.CloudFunctionality;
import com.sequenceiq.it.cloudbreak.util.FreeIpaInstanceUtil;
import com.sequenceiq.it.cloudbreak.util.spot.UseSpotInstances;

public class AzureEnvironmentWithCustomerManagedKeyTests extends AbstractE2ETest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsEnvironmentWithCustomerManagedKeyTests.class);

    private static final String RESOURCE_GROUP_FOR_TEST = "azure-test-des-rg";

    @Inject
    private EnvironmentTestClient environmentTestClient;

    @Inject
    private CredentialTestClient credentialTestClient;

    @Inject
    private Azure azure;

    @Inject
    private CommonCloudProperties commonCloudProperties;

    @Inject
    private AzureCloudProvider azureCloudProvider;

    @Inject
    private FreeIpaTestClient freeIpaTestClient;

    @Inject
    private FreeIpaInstanceUtil freeIpaInstanceUtil;

    @Override
    protected void setupTest(TestContext testContext) {
        checkCloudPlatform(CloudPlatform.AZURE);
        createDefaultUser(testContext);
    }

    @AfterMethod
    public void tearDownSpot() {
        deleteResourceGroupCreatedForEnvironment(RESOURCE_GROUP_FOR_TEST);
    }

    @Test(dataProvider = TEST_CONTEXT, description = "Creating a resource group for this test case. Disk encryption set(DES)" +
            " is created in this newly created RG, as cloud-daily RG has locks which prevents cleanup of DES.")
    @UseSpotInstances
    @Description(
            given = "there is a running cloudbreak",
            when = "create an Environment with disk encryption where key and environment are in same Resource groups",
            then = "should use encryption parameters for resource encryption.")
    public void testWithEnvironmentResourceGroup(TestContext testContext) {
        createResourceGroupForEnvironment(RESOURCE_GROUP_FOR_TEST);

        testContext
                .given(CredentialTestDto.class)
                .when(credentialTestClient.create())
                .given(EnvironmentNetworkTestDto.class)
                .given("telemetry", TelemetryTestDto.class)
                    .withLogging()
                    .withReportClusterLogs()
                .given(EnvironmentTestDto.class)
                    .withName("azure-test-des-1")
                    .withNetwork()
                    .withResourceGroup("SINGLE", RESOURCE_GROUP_FOR_TEST)
                    .withResourceEncryption()
                    .withTelemetry("telemetry")
                    .withTunnel(Tunnel.CLUSTER_PROXY)
                    .withCreateFreeIpa(Boolean.FALSE)
                .when(environmentTestClient.create())
                .await(EnvironmentStatus.AVAILABLE)
                .given(FreeIpaTestDto.class)
                    .withEnvironment()
                    .withTelemetry("telemetry")
                    .withCatalog(commonCloudProperties().getImageValidation().getFreeIpaImageCatalog(),
                        commonCloudProperties().getImageValidation().getFreeIpaImageUuid())
                .when(freeIpaTestClient.create())
                .await(Status.AVAILABLE)
                .awaitForHealthyInstances()
                .given(FreeIpaUserSyncTestDto.class)
                .when(freeIpaTestClient.getLastSyncOperationStatus())
                .await(OperationState.COMPLETED)
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.describe())
                .then(this::verifyEnvironmentResponseDesParameters)
                .given(FreeIpaTestDto.class)
                .then(this::verifyFreeIpaVolumeDesKey)
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT, description = "Environment's Resource Group is not specified, in this case all the resources create their own" +
            " resource groups.")
    @UseSpotInstances
    @Description(
            given = "there is a running cloudbreak",
            when = "create an Environment with disk encryption where key and environment are in different Resource groups",
            then = "should use encryption parameters for resource encryption.")
    public void testWithEncryptionKeyResourceGroup(TestContext testContext) {
        testContext
                .given(CredentialTestDto.class)
                .when(credentialTestClient.create())
                .given(EnvironmentNetworkTestDto.class)
                .given("telemetry", TelemetryTestDto.class)
                    .withLogging()
                    .withReportClusterLogs()
                .given(EnvironmentTestDto.class)
                    .withName("azure-test-des-2")
                    .withNetwork()
                    .withResourceGroup("USE_MULTIPLE", null)
                    .withResourceEncryption()
                    .withTelemetry("telemetry")
                    .withTunnel(Tunnel.CLUSTER_PROXY)
                    .withCreateFreeIpa(Boolean.FALSE)
                .when(environmentTestClient.create())
                .await(EnvironmentStatus.AVAILABLE)
                .given(FreeIpaTestDto.class)
                    .withEnvironment()
                    .withTelemetry("telemetry")
                    .withCatalog(commonCloudProperties().getImageValidation().getFreeIpaImageCatalog(),
                        commonCloudProperties().getImageValidation().getFreeIpaImageUuid())
                .when(freeIpaTestClient.create())
                .await(Status.AVAILABLE)
                .awaitForHealthyInstances()
                .given(FreeIpaUserSyncTestDto.class)
                .when(freeIpaTestClient.getLastSyncOperationStatus())
                .await(OperationState.COMPLETED)
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.describe())
                .then(this::verifyEnvironmentResponseDesParameters)
                .given(FreeIpaTestDto.class)
                .then(this::verifyFreeIpaVolumeDesKey)
                .validate();
    }

    private ResourceGroup createResourceGroupForEnvironment(String resourceGroupName) {
        return azure.resourceGroups().define(resourceGroupName)
                .withRegion("West US 2")
                .withTags(commonCloudProperties.getTags())
                .create();
    }

    private boolean resourceGroupExists(String name) {
        try {
            return azure.resourceGroups().contain(name);
        } catch (CloudException e) {
            if (e.getMessage().contains("Status code 403")) {
                return false;
            }
            throw e;
        }
    }

    private void deleteResourceGroupCreatedForEnvironment(String resourceGroupName) {
        if (resourceGroupExists(resourceGroupName)) {
            azure.resourceGroups().deleteByName(resourceGroupName);
        }
    }

    private EnvironmentTestDto verifyEnvironmentResponseDesParameters(TestContext testContext, EnvironmentTestDto testDto,
            EnvironmentClient environmentClient) {
        DetailedEnvironmentResponse environment = environmentClient.getDefaultClient().environmentV1Endpoint().getByName(testDto.getName());

        if (CloudPlatform.AZURE.name().equals(environment.getCloudPlatform())) {
            String encryptionKey = environment.getAzure().getResourceEncryptionParameters().getEncryptionKeyUrl();
            String environmentName = testDto.getRequest().getName();

            if (StringUtils.isEmpty(encryptionKey)) {
                LOGGER.error(String.format("DES key is not available for '%s' environment!", environmentName));
                throw new TestFailException(format("DES key is not available for '%s' environment!", environmentName));
            } else {
                LOGGER.info(String.format(" Environment '%s' create has been done with '%s' DES key. ", environmentName, encryptionKey));
                Log.then(LOGGER, format(" Environment '%s' create has been done with '%s' DES key. ", environmentName, encryptionKey));
            }
        }
        return testDto;
    }

    private FreeIpaTestDto verifyFreeIpaVolumeDesKey(TestContext testContext, FreeIpaTestDto testDto, FreeIpaClient freeIpaClient) {
        CloudFunctionality cloudFunctionality = testContext.getCloudProvider().getCloudFunctionality();
        List<String> instanceIds = freeIpaInstanceUtil.getInstanceIds(testDto, freeIpaClient, MASTER.getName());
        String desKeyUrl = azureCloudProvider.getEncryptionKeyUrl();

        List<String> volumesDesId = new ArrayList<>(cloudFunctionality.listVolumeEncryptionKeyIds(testDto.getRequest().getName(), instanceIds));
        volumesDesId.forEach(desId -> {
            if (desId.contains("diskEncryptionSets/azure-test-des")) {
                LOGGER.info(format("FreeIpa volume has been encrypted with '%s' DES key!", desId));
                Log.then(LOGGER, format(" FreeIpa volume has not been encrypted with '%s' DES key! ", desId));
            } else {
                LOGGER.error("FreeIpa volume has not been encrypted with [{}] DES key!", desKeyUrl);
                throw new TestFailException(format("FreeIpa volume has not been encrypted with [%s] DES key!", desKeyUrl));
            }
        });
        return testDto;
    }
}