package com.sequenceiq.cloudbreak.cloud.event.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformResult;
import com.sequenceiq.cloudbreak.cloud.model.CloudResourceStatus;
import com.sequenceiq.cloudbreak.common.event.FlowPayload;

public class LaunchLoadBalancerResult extends CloudPlatformResult implements FlowPayload {
    private final List<CloudResourceStatus> results;

    @JsonCreator
    public LaunchLoadBalancerResult(
            @JsonProperty("resourceId") Long resourceId,
            @JsonProperty("results") List<CloudResourceStatus> results) {
        super(resourceId);
        this.results = results;
    }

    public LaunchLoadBalancerResult(Exception errorDetails, Long resourceId) {
        super("", errorDetails, resourceId);
        this.results = null;
    }

    public List<CloudResourceStatus> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "LaunchLoadBalancerResult{"
            + "status=" + getStatus()
            + ", statusReason='" + getStatusReason() + '\''
            + ", errorDetails=" + getErrorDetails()
            + ", results=" + results
            + '}';
    }
}
