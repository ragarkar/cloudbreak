package com.sequenceiq.environment.environment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.sequenceiq.environment.parameter.dto.AwsDiskEncryptionParametersDto;

public class UpdateAwsDiskEncryptionParametersDtoTest {

    @Test
    void builderTest() {
        UpdateAwsDiskEncryptionParametersDto underTest = new UpdateAwsDiskEncryptionParametersDto();
        underTest.setAwsDiskEncryptionParametersDto(AwsDiskEncryptionParametersDto.builder().build());

        assertThat(underTest.getAwsDiskEncryptionParametersDto()).isNotNull();
    }
}
