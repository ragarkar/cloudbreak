package com.sequenceiq.freeipa.api.v1.freeipa.stack.model.reboot;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.auth.crn.CrnResourceDescriptor;
import com.sequenceiq.cloudbreak.validation.ValidCrn;
import com.sequenceiq.service.api.doc.ModelDescriptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RebootInstancesV1Request")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RebootInstancesRequest {
    @ValidCrn(resource = CrnResourceDescriptor.ENVIRONMENT)
    @NotEmpty
    @Schema(description = ModelDescriptions.ENVIRONMENT_CRN, required = true)
    private String environmentCrn;

    @Schema(description = ModelDescriptions.FORCE_REBOOT)
    private boolean forceReboot;

    @Schema(description = ModelDescriptions.INSTANCE_ID)
    private List<String> instanceIds;

    public String getEnvironmentCrn() {
        return environmentCrn;
    }

    public void setEnvironmentCrn(String environmentCrn) {
        this.environmentCrn = environmentCrn;
    }

    public boolean isForceReboot() {
        return forceReboot;
    }

    public void setForceReboot(boolean forceReboot) {
        this.forceReboot = forceReboot;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    @Override
    public String toString() {
        return "RebootInstancesRequest{" +
                "environmentCrn='" + environmentCrn + '\'' +
                ", forceReboot=" + forceReboot +
                ", instanceIds=" + instanceIds +
                '}';
    }
}
