package com.sequenceiq.environment.api.v1.environment.model.request.aws;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UpdateAwsDiskEncryptionParametersRequestTest {
    private static final String ENCRYPTION_KEY_ARN = "encryptionKeyArn";

    @Test
    void builderTest() {
        UpdateAwsDiskEncryptionParametersRequest underTest = new UpdateAwsDiskEncryptionParametersRequest();
        underTest.setAwsDiskEncryptionParameters(AwsDiskEncryptionParameters.builder()
                .withEncryptionKeyArn(ENCRYPTION_KEY_ARN)
                .build()
        );
        assertThat(underTest.getAwsDiskEncryptionParameters().getEncryptionKeyArn()).isEqualTo(ENCRYPTION_KEY_ARN);
    }
}
