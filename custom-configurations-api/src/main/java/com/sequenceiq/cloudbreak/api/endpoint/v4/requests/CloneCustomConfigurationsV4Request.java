package com.sequenceiq.cloudbreak.api.endpoint.v4.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.doc.ApiDescription.CustomConfigurationsJsonProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloneCustomConfigurationsV4Request {

    @Schema(description = CustomConfigurationsJsonProperties.CUSTOM_CONFIGURATIONS_NAME)
    @Size(min = 1, max = 100,
            message = "Length of custom configurations name must be from 1 to 100 characters and shouldn't contain semicolon and percentage symbol")
    @Pattern(regexp = "^[^;\\/%]*$")
    @NotNull
    private String name;

    @Schema(description = CustomConfigurationsJsonProperties.RUNTIME_VERSION)
    private String runtimeVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    public void setRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }
}
