package com.sequenceiq.freeipa.api.v1.freeipa.stack.model.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.common.model.JsonEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolumeParameterConfigResponse implements JsonEntity {

    private String volumeParameterType;

    private Integer minimumSize;

    private Integer maximumSize;

    private Integer minimumNumber;

    private Integer maximumNumber;

    public String getVolumeParameterType() {
        return volumeParameterType;
    }

    public void setVolumeParameterType(String volumeParameterType) {
        this.volumeParameterType = volumeParameterType;
    }

    public Integer getMinimumSize() {
        return minimumSize;
    }

    public void setMinimumSize(Integer minimumSize) {
        this.minimumSize = minimumSize;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Integer getMinimumNumber() {
        return minimumNumber;
    }

    public void setMinimumNumber(Integer minimumNumber) {
        this.minimumNumber = minimumNumber;
    }

    public Integer getMaximumNumber() {
        return maximumNumber;
    }

    public void setMaximumNumber(Integer maximumNumber) {
        this.maximumNumber = maximumNumber;
    }

    @Override
    public String toString() {
        return "VolumeParameterConfigResponse{" +
                "volumeParameterType='" + volumeParameterType + '\'' +
                ", minimumSize=" + minimumSize +
                ", maximumSize=" + maximumSize +
                ", minimumNumber=" + minimumNumber +
                ", maximumNumber=" + maximumNumber +
                '}';
    }
}
