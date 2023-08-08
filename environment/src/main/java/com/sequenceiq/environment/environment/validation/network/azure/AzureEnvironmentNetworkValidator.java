package com.sequenceiq.environment.environment.validation.network.azure;

import static com.sequenceiq.cloudbreak.common.mappable.CloudPlatform.AZURE;
import static com.sequenceiq.environment.environment.validation.ValidationType.ENVIRONMENT_CREATION;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.auth.altus.EntitlementService;
import com.sequenceiq.cloudbreak.cloud.azure.AzureCloudSubnetParametersService;
import com.sequenceiq.cloudbreak.cloud.model.CloudSubnet;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.validation.ValidationResult.ValidationResultBuilder;
import com.sequenceiq.environment.environment.domain.Region;
import com.sequenceiq.environment.environment.dto.EnvironmentDto;
import com.sequenceiq.environment.environment.dto.EnvironmentValidationDto;
import com.sequenceiq.environment.environment.validation.network.EnvironmentNetworkValidator;
import com.sequenceiq.environment.network.CloudNetworkService;
import com.sequenceiq.environment.network.dto.AzureParams;
import com.sequenceiq.environment.network.dto.NetworkDto;

@Component
public class AzureEnvironmentNetworkValidator implements EnvironmentNetworkValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureEnvironmentNetworkValidator.class);

    private final CloudNetworkService cloudNetworkService;

    private final AzurePrivateEndpointValidator azurePrivateEndpointValidator;

    private final AzureCloudSubnetParametersService azureCloudSubnetParametersService;

    private final EntitlementService entitlementService;

    @Value("${cb.multiaz.azure.availabilityZones}")
    private Set<String> azureAvailabilityZones;

    public AzureEnvironmentNetworkValidator(CloudNetworkService cloudNetworkService,
            AzurePrivateEndpointValidator azurePrivateEndpointValidator, AzureCloudSubnetParametersService azureCloudSubnetParametersService,
            EntitlementService entitlementService) {
        this.cloudNetworkService = cloudNetworkService;
        this.azurePrivateEndpointValidator = azurePrivateEndpointValidator;
        this.azureCloudSubnetParametersService = azureCloudSubnetParametersService;
        this.entitlementService = entitlementService;
    }

    @Override
    public void validateDuringFlow(EnvironmentValidationDto environmentValidationDto, NetworkDto networkDto, ValidationResultBuilder resultBuilder) {
        if (environmentValidationDto == null || environmentValidationDto.getEnvironmentDto() == null || networkDto == null) {
            LOGGER.warn("Neither EnvironmentDto nor NetworkDto could be null!");
            resultBuilder.error("Internal validation error");
            return;
        }
        EnvironmentDto environmentDto = environmentValidationDto.getEnvironmentDto();
        Map<String, CloudSubnet> cloudNetworks = cloudNetworkService.retrieveSubnetMetadata(environmentDto, networkDto);
        Region region = environmentDto.getRegions()
                .stream().findFirst()
                .orElseThrow();
        checkSubnetsProvidedWhenExistingNetwork(resultBuilder, "subnet IDs", networkDto.getSubnetIds(), networkDto.getAzure(), cloudNetworks, region);
        checkFlexibleServerSubnetIds(networkDto.getAzure(), environmentValidationDto.getEnvironmentDto(), networkDto, resultBuilder);
        if (CollectionUtils.isNotEmpty(networkDto.getEndpointGatewaySubnetIds())) {
            LOGGER.debug("Checking EndpointGatewaySubnetIds {}", networkDto.getEndpointGatewaySubnetIds());
            Map<String, CloudSubnet> endpointGatewayNetworks = cloudNetworkService.retrieveEndpointGatewaySubnetMetadata(environmentDto, networkDto);
            checkSubnetsProvidedWhenExistingNetwork(resultBuilder, "endpoint gateway subnet IDs",
                    networkDto.getEndpointGatewaySubnetIds(), networkDto.getAzure(), endpointGatewayNetworks, region);
        }
        if (environmentValidationDto.getValidationType() == ENVIRONMENT_CREATION) {
            azurePrivateEndpointValidator.checkNetworkPoliciesWhenExistingNetwork(networkDto, cloudNetworks, resultBuilder);
            azurePrivateEndpointValidator.checkMultipleResourceGroup(resultBuilder, environmentDto, networkDto);
            azurePrivateEndpointValidator.checkExistingManagedPrivateDnsZone(resultBuilder, environmentDto, networkDto);
            azurePrivateEndpointValidator.checkNewPrivateDnsZone(resultBuilder, environmentDto, networkDto);
            azurePrivateEndpointValidator.checkExistingRegisteredOnlyPrivateDnsZone(resultBuilder, environmentDto, networkDto);
        } else {
            LOGGER.debug("Skipping Private Endpoint related validations as they have been validated before during env creation");
        }
    }

    @Override
    public void validateDuringRequest(EnvironmentValidationDto environmentValidationDto, NetworkDto networkDto, ValidationResultBuilder resultBuilder) {
        if (networkDto == null) {
            return;
        }
        checkEitherNetworkCidrOrNetworkIdIsPresent(networkDto, resultBuilder);
        AzureParams azureParams = networkDto.getAzure();
        if (azureParams != null) {
            checkSubnetsProvidedWhenExistingNetwork(resultBuilder, azureParams, networkDto.getSubnetMetas());
            checkExistingNetworkParamsProvidedWhenSubnetsPresent(networkDto, resultBuilder);
            checkResourceGroupNameWhenExistingNetwork(resultBuilder, azureParams);
            checkNetworkIdWhenExistingNetwork(resultBuilder, azureParams);
            checkNetworkIdIsSpecifiedWhenSubnetIdsArePresent(resultBuilder, azureParams, networkDto);
            validateAvailabilityZones(environmentValidationDto, resultBuilder, azureParams);
        } else if (StringUtils.isEmpty(networkDto.getNetworkCidr())) {
            resultBuilder.error(missingParamsErrorMsg(AZURE));
        }
    }

    @Override
    public void validateDuringRequest(NetworkDto networkDto, ValidationResultBuilder resultBuilder) {
        validateDuringRequest(null, networkDto, resultBuilder);
    }

    private void checkEitherNetworkCidrOrNetworkIdIsPresent(NetworkDto networkDto, ValidationResultBuilder resultBuilder) {
        if (StringUtils.isEmpty(networkDto.getNetworkCidr()) && StringUtils.isEmpty(networkDto.getNetworkId())) {
            String message = "Either the AZURE networkId or CIDR needs to be defined!";
            LOGGER.info(message);
            resultBuilder.error(message);
        }
    }

    private void checkSubnetsProvidedWhenExistingNetwork(ValidationResultBuilder resultBuilder, String context,
            Set<String> subnetIds, AzureParams azureParams, Map<String, CloudSubnet> subnetMetas, Region region) {
        if (StringUtils.isNotEmpty(azureParams.getNetworkId()) && StringUtils.isNotEmpty(azureParams.getResourceGroupName())) {
            if (CollectionUtils.isEmpty(subnetIds)) {
                String message = String.format("If networkId (%s) and resourceGroupName (%s) are specified then %s must be specified as well.",
                        azureParams.getNetworkId(), azureParams.getResourceGroupName(), context);
                LOGGER.info(message);
                resultBuilder.error(message);
            } else if (subnetMetas.size() != subnetIds.size()) {
                String message = String.format("If networkId (%s) and resourceGroupName (%s) are specified then %s must be specified and should exist " +
                                "on Azure as well. Given %s: [%s], existing ones: [%s], in region: [%s]", azureParams.getNetworkId(),
                        azureParams.getResourceGroupName(), context, context,
                        subnetIds.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")),
                        subnetMetas.keySet().stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")), region.getDisplayName());
                LOGGER.info(message);
                resultBuilder.error(message);
            }
        }
    }

    private void checkResourceGroupNameWhenExistingNetwork(ValidationResultBuilder resultBuilder, AzureParams azureParams) {
        if (StringUtils.isNotEmpty(azureParams.getNetworkId()) && StringUtils.isEmpty(azureParams.getResourceGroupName())) {
            resultBuilder.error("If networkId is specified, then resourceGroupName must be specified too.");
        }
    }

    private void checkNetworkIdWhenExistingNetwork(ValidationResultBuilder resultBuilder, AzureParams azureParams) {
        if (StringUtils.isEmpty(azureParams.getNetworkId()) && StringUtils.isNotEmpty(azureParams.getResourceGroupName())) {
            resultBuilder.error("If resourceGroupName is specified, then networkId must be specified too.");
        }
    }

    private void checkExistingNetworkParamsProvidedWhenSubnetsPresent(NetworkDto networkDto, ValidationResultBuilder resultBuilder) {
        if ((CollectionUtils.isNotEmpty(networkDto.getSubnetIds()) || CollectionUtils.isNotEmpty(networkDto.getEndpointGatewaySubnetIds()))
                && StringUtils.isEmpty(networkDto.getAzure().getNetworkId())
                && StringUtils.isEmpty(networkDto.getAzure().getResourceGroupName())) {
            String message =
                    String.format("If %s subnet IDs were provided then network id and resource group name have to be specified, too.", AZURE.name());
            LOGGER.info(message);
            resultBuilder.error(message);
        }
    }

    private void checkNetworkIdIsSpecifiedWhenSubnetIdsArePresent(ValidationResultBuilder resultBuilder,
            AzureParams azureParams, NetworkDto networkDto) {
        if (StringUtils.isEmpty(azureParams.getNetworkId()) && CollectionUtils.isNotEmpty(networkDto.getSubnetIds())) {
            resultBuilder.error("If subnetIds are specified, then networkId must be specified too.");
        }
    }

    private void checkSubnetsProvidedWhenExistingNetwork(ValidationResultBuilder resultBuilder,
            AzureParams azureParams, Map<String, CloudSubnet> subnetMetas) {
        if (StringUtils.isNotEmpty(azureParams.getNetworkId()) && StringUtils.isNotEmpty(azureParams.getResourceGroupName())
                && MapUtils.isEmpty(subnetMetas)) {
            String message = String.format("If networkId (%s) and resourceGroupName (%s) are specified then subnet ids must be specified as well.",
                    azureParams.getNetworkId(), azureParams.getResourceGroupName());
            LOGGER.info(message);
            resultBuilder.error(message);
        }
    }

    private void validateAvailabilityZones(EnvironmentValidationDto environmentValidationDto, ValidationResultBuilder resultBuilder, AzureParams azureParams) {
        if (CollectionUtils.isNotEmpty(azureParams.getAvailabilityZones())) {
            LOGGER.debug("Availability zones are {}", azureParams.getAvailabilityZones());
            boolean allZonesValid = checkInvalidAvailabilityZones(azureParams, resultBuilder);
            if (allZonesValid) {
                Set<String> existingAvailabilityZones = getAvailabilityZones(environmentValidationDto);
                if (CollectionUtils.isNotEmpty(existingAvailabilityZones)) {
                    if (!CollectionUtils.containsAll(azureParams.getAvailabilityZones(), existingAvailabilityZones)) {
                        String message = String.format("Provided Availability Zones for environment do not contain the existing Availability Zones. " +
                                        "Provided Availability Zones : %s. Existing Availability Zones : %s", azureParams.getAvailabilityZones()
                                        .stream().sorted().collect(Collectors.joining(",")),
                                existingAvailabilityZones.stream().sorted().collect(Collectors.joining(",")));
                        LOGGER.info(message);
                        resultBuilder.error(message);
                    }
                }
            }
        }
    }

    private Set<String> getAvailabilityZones(EnvironmentValidationDto environmentValidationDto) {
        Set<String> availabilityZones = null;
        if (environmentValidationDto != null && environmentValidationDto.getEnvironmentDto() != null
                && environmentValidationDto.getEnvironmentDto().getNetwork() != null
                && environmentValidationDto.getEnvironmentDto().getNetwork().getAzure() != null) {
            availabilityZones = environmentValidationDto.getEnvironmentDto().getNetwork().getAzure().getAvailabilityZones();
        }
        return  availabilityZones;
    }

    private boolean checkInvalidAvailabilityZones(AzureParams azureParams, ValidationResultBuilder resultBuilder) {
        Set<String> invalidAvailabilityZones = azureParams.getAvailabilityZones().stream().filter(az -> !azureAvailabilityZones.contains(az))
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(invalidAvailabilityZones)) {
            String message = String.format("Availability zones %s are not valid. Valid availability zones are %s.",
                    invalidAvailabilityZones.stream().sorted().collect(Collectors.joining(",")),
                    azureAvailabilityZones.stream().sorted().collect(Collectors.joining(",")));
            LOGGER.info(message);
            resultBuilder.error(message);
            return false;
        } else {
            return true;
        }
    }

    private void checkFlexibleServerSubnetIds(AzureParams azureParams, EnvironmentDto environmentDto, NetworkDto networkDto,
            ValidationResultBuilder resultBuilder) {
        Set<String> flexibleServerSubnetIds = azureParams.getFlexibleServerSubnetIds().stream()
                .map(subnet -> {
                    if (subnet.contains("/")) {
                        return StringUtils.substringAfterLast(subnet, "/");
                    } else {
                        return subnet;
                    }
                })
                .collect(Collectors.toSet());
        if (entitlementService.isAzureDatabaseFlexibleServerEnabled(environmentDto.getAccountId()) && CollectionUtils.isNotEmpty(flexibleServerSubnetIds)) {
            Map<String, CloudSubnet> flexibleSubnets = cloudNetworkService.getSubnetMetadata(environmentDto, networkDto, flexibleServerSubnetIds);
            Set<String> invalidSubnets = flexibleSubnets.entrySet().stream()
                    .filter(entry -> !azureCloudSubnetParametersService.isFlexibleServerDelegatedSubnet(entry.getValue()))
                    .map(Entry::getKey)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(invalidSubnets)) {
                String message = String.format("The following subnets are not delegated to flexible servers: %s", String.join(",", invalidSubnets));
                LOGGER.info(message);
                resultBuilder.error(message);
            }
        }
    }

    @Override
    public CloudPlatform getCloudPlatform() {
        return AZURE;
    }

}
