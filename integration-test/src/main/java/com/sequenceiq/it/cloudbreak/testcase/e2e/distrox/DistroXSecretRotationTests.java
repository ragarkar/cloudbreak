package com.sequenceiq.it.cloudbreak.testcase.e2e.distrox;

import static com.sequenceiq.cloudbreak.rotation.CloudbreakSecretType.USER_KEYPAIR;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_USER_KEYPAIR;

import java.util.Set;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.it.cloudbreak.client.DistroXTestClient;
import com.sequenceiq.it.cloudbreak.client.EnvironmentTestClient;
import com.sequenceiq.it.cloudbreak.client.FreeIpaTestClient;
import com.sequenceiq.it.cloudbreak.client.SdxTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.distrox.DistroXTestDto;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentAuthenticationTestDto;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentTestDto;
import com.sequenceiq.it.cloudbreak.dto.sdx.SdxInternalTestDto;
import com.sequenceiq.it.cloudbreak.log.Log;
import com.sequenceiq.it.cloudbreak.testcase.e2e.AbstractE2ETest;
import com.sequenceiq.it.cloudbreak.util.DistroxUtil;
import com.sequenceiq.it.cloudbreak.util.SecretRotationCheckUtil;
import com.sequenceiq.it.cloudbreak.util.spot.UseSpotInstances;

public class DistroXSecretRotationTests extends AbstractE2ETest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistroXSecretRotationTests.class);

    private static final String LDAP_CHECK_COMMAND = "export LDAP_BIND_PW=$(sudo grep \"LDAP_BIND_PW\" /etc/cloudera-scm-server/cm.settings | " +
            "awk '{print $3;}') && " +
            "export LDAP_BIND_DN=$(sudo grep \"LDAP_BIND_DN\" /etc/cloudera-scm-server/cm.settings | awk '{print $3;}') && " +
            "export LDAP_USER_SEARCH_BASE=$(sudo grep \"LDAP_USER_SEARCH_BASE\" /etc/cloudera-scm-server/cm.settings | awk '{print $3;}') && " +
            "export LDAP_URL=$(sudo grep \"LDAP_URL\" /etc/cloudera-scm-server/cm.settings | awk '{print $3;}') && " +
            "ldapsearch -LLL -H $LDAP_URL -D $LDAP_BIND_DN -w $LDAP_BIND_PW -b $LDAP_USER_SEARCH_BASE -z1";

    @Inject
    private SdxTestClient sdxTestClient;

    @Inject
    private DistroXTestClient distroXTestClient;

    @Inject
    private DistroxUtil distroxUtil;

    @Inject
    private EnvironmentTestClient environmentTestClient;

    @Inject
    private FreeIpaTestClient freeIpaTestClient;

    @Inject
    private SecretRotationCheckUtil secretRotationCheckUtil;

    @Override
    protected void setupTest(TestContext testContext) {
        assertSupportedCloudPlatform(CloudPlatform.AWS);
        testContext.getCloudProvider().getCloudFunctionality().cloudStorageInitialize();
        createDefaultUser(testContext);
        createDefaultCredential(testContext);
        initializeDefaultBlueprints(testContext);
        createDefaultDatahubWithAutoTlsAndExternalDb(testContext);
    }

    @Test(dataProvider = TEST_CONTEXT)
    @UseSpotInstances
    @Description(
            given = "there is an environment with DistroX in available state",
            when = "SSH secrets are getting rotated if the required test parameters are present",
            then = "rotation should be successful, the cluster should be available"
    )
    public void testSSHKeyPairRotation(TestContext testContext) {
        if (StringUtils.isBlank(commonCloudProperties().getRotationSshPublicKey()) ||
                StringUtils.isBlank(commonCloudProperties().getRotationPrivateKeyFile())) {
            Log.log(LOGGER, "SSH key rotation skipped because parameters are not found " +
                    "(integrationtest.rotationSshPublicKey, integrationtest.rotationPrivateKeyFile).");
        } else {
            testContext
                    .given(EnvironmentAuthenticationTestDto.class)
                    .withPublicKey(commonCloudProperties().getRotationSshPublicKey())
                    .given(EnvironmentTestDto.class)
                    .when(environmentTestClient.changeAuthentication())
                    .given(SdxInternalTestDto.class)
                    .when(sdxTestClient.rotateSecret(Set.of(DATALAKE_USER_KEYPAIR)))
                    .awaitForFlow()
                    .then((tc, testDto, client) -> {
                        secretRotationCheckUtil.checkSSHLoginWithNewKeys(testDto.getCrn(), client);
                        return testDto;
                    })
                    .given(DistroXTestDto.class)
                    .when(distroXTestClient.rotateSecret(Set.of(USER_KEYPAIR)))
                    .awaitForFlow()
                    .then((tc, testDto, client) -> {
                        secretRotationCheckUtil.checkSSHLoginWithNewKeys(tc, testDto, client);
                        return testDto;
                    })
                    .validate();
        }
    }
}
