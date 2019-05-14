package com.sequenceiq.cloudbreak.controller.validation.environment.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.base.EnvironmentNetworkAzureV4Params;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentNetworkV4Request;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult;

@RunWith(MockitoJUnitRunner.class)
public class AzureEnvironmentNetworkValidatorTest {

    private static final String NETWORK_CIDR = "10.0.0.0/16";

    private static final Set<String> SUBNET_IDS = Set.of("subnet-1", "subnet-2");

    private static final String NETWORK_ID = "network-id";

    private static final String RESOURCE_GROUP = "resourceGroup";

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureEnvironmentNetworkValidatorTest.class);

    @InjectMocks
    private AzureEnvironmentNetworkValidator underTest;

    private ValidationResult.ValidationResultBuilder validationResult;

    private EnvironmentNetworkV4Request networkV4Request;

    @Before
    public void before() {
        validationResult = new ValidationResult.ValidationResultBuilder();
        networkV4Request = new EnvironmentNetworkV4Request();
    }

    @Test
    public void testValidateShouldReturnsWithErrorWhenTheEnvironmentNetworkV4RequestIsNull() {
        underTest.validate(null, validationResult);

        assertTrue(validationResult.build().hasError());
        assertEquals(1, validationResult.build().getErrors().size());
        LOGGER.info(validationResult.build().getFormattedErrors());
    }

    @Test
    public void testValidateShouldReturnsWithoutErrorsWhenAllParameterValidInCaseOfNewNetwork() {
        networkV4Request.setNetworkCidr(NETWORK_CIDR);

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithoutErrorsWhenAllParameterValidInCaseOfExistingNetwork() {
        networkV4Request.setSubnetIds(SUBNET_IDS);
        networkV4Request.setAzure(azureNetwork());

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithOutErrorWhenTheSubnetCidrIsNotPresentInCaseOfExistingNetwork() {
        networkV4Request.setNetworkCidr(NETWORK_CIDR);
        networkV4Request.setSubnetIds(SUBNET_IDS);
        networkV4Request.setAzure(azureNetwork());

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithOutErrorWhenTheNetworkCidrIsNotPresentInCaseOfExistingNetwork() {
        networkV4Request.setSubnetIds(SUBNET_IDS);
        networkV4Request.setAzure(azureNetwork());

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithOutErrorWhenTheVpcIdIsNotPresentInCaseOfNewNetwork() {
        networkV4Request.setNetworkCidr(NETWORK_CIDR);
        networkV4Request.setSubnetIds(SUBNET_IDS);

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithOutErrorWhenTheSubnetIdIsNotPresentInCaseOfNewNetwork() {
        networkV4Request.setAzure(azureNetwork());
        networkV4Request.setNetworkCidr(NETWORK_CIDR);

        underTest.validate(networkV4Request, validationResult);

        assertFalse(validationResult.build().hasError());
    }

    @Test
    public void testValidateShouldReturnsWithErrorWhenTheNetworkCidrIsNotValidInCaseOfNewNetwork() {
        networkV4Request.setNetworkCidr(NETWORK_CIDR);
        validationResult.error("Cidr validation error.");

        underTest.validate(networkV4Request, validationResult);

        assertTrue(validationResult.build().hasError());
        assertEquals(1, validationResult.build().getErrors().size());
        LOGGER.info(validationResult.build().getFormattedErrors());
    }

    @Test
    public void testValidateShouldReturnsWithErrorWhenTheResourceGroupIsMissingInCaseOfNewNetwork() {
        networkV4Request.setSubnetIds(SUBNET_IDS);
        networkV4Request.setAzure(azureNetworkWithoutResourceGroup());

        underTest.validate(networkV4Request, validationResult);

        assertTrue(validationResult.build().hasError());
        assertEquals(1, validationResult.build().getErrors().size());
        LOGGER.info(validationResult.build().getFormattedErrors());
    }

    @Test
    public void testValidateShouldReturnsWithErrorsWhenTheNetworkIdAndNetworkCidrAreNotPresent() {
        networkV4Request.setSubnetIds(SUBNET_IDS);
        networkV4Request.setAzure(azureNetworkWithoutNetworkId());

        underTest.validate(networkV4Request, validationResult);

        assertTrue(validationResult.build().hasError());
        assertEquals(2, validationResult.build().getErrors().size());
        LOGGER.info(validationResult.build().getFormattedErrors());
    }

    @Test
    public void testValidateShouldReturnsWithErrorsWhenAllParameterIsMissing() {
        underTest.validate(networkV4Request, validationResult);

        assertTrue(validationResult.build().hasError());
        assertEquals(4, validationResult.build().getErrors().size());
        LOGGER.info(validationResult.build().getFormattedErrors());
    }

    private EnvironmentNetworkAzureV4Params azureNetwork() {
        EnvironmentNetworkAzureV4Params azureV4Params = new EnvironmentNetworkAzureV4Params();
        azureV4Params.setNetworkId(NETWORK_ID);
        azureV4Params.setResourceGroupName(RESOURCE_GROUP);
        return azureV4Params;
    }

    private EnvironmentNetworkAzureV4Params azureNetworkWithoutResourceGroup() {
        EnvironmentNetworkAzureV4Params azureV4Params = new EnvironmentNetworkAzureV4Params();
        azureV4Params.setNetworkId(NETWORK_ID);
        return azureV4Params;
    }

    private EnvironmentNetworkAzureV4Params azureNetworkWithoutNetworkId() {
        EnvironmentNetworkAzureV4Params azureV4Params = new EnvironmentNetworkAzureV4Params();
        azureV4Params.setResourceGroupName(RESOURCE_GROUP);
        return azureV4Params;
    }

}