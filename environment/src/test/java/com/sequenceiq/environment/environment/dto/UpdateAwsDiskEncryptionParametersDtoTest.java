package com.sequenceiq.environment.environment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.sequenceiq.environment.api.v1.environment.model.request.aws.AwsDiskEncryptionParameters;
import com.sequenceiq.environment.api.v1.environment.model.request.aws.UpdateAwsDiskEncryptionParametersRequest;
import com.sequenceiq.environment.parameter.dto.AwsDiskEncryptionParametersDto;

public class UpdateAwsDiskEncryptionParametersDtoTest {

    @Test
    void builderTest() {
        AwsDiskEncryptionParametersDto awsDiskEncryptionParametersDto = AwsDiskEncryptionParametersDto.builder().withEncryptionKeyArn("dummyEncryptionKeyArn").build();
        UpdateAwsDiskEncryptionParametersDto underTest = UpdateAwsDiskEncryptionParametersDto.builder().withAwsDiskEncryptionParametersDto(awsDiskEncryptionParametersDto).build();
        //underTest.setAwsDiskEncryptionParametersDto(AwsDiskEncryptionParametersDto.builder().build());

        assertThat(underTest.getAwsDiskEncryptionParametersDto()).isNotNull();
    }
}
