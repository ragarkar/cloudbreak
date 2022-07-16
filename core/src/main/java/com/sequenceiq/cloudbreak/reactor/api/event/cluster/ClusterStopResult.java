package com.sequenceiq.cloudbreak.reactor.api.event.cluster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.common.event.FlowPayload;
import com.sequenceiq.cloudbreak.reactor.api.ClusterPlatformResult;

public class ClusterStopResult extends ClusterPlatformResult<ClusterStopRequest> implements FlowPayload {
    public ClusterStopResult(ClusterStopRequest request) {
        super(request);
    }

    @JsonCreator
    public ClusterStopResult(
            @JsonProperty("statusReason") String statusReason,
            @JsonProperty("errorDetails") Exception errorDetails,
            @JsonProperty("request") ClusterStopRequest request) {
        super(statusReason, errorDetails, request);
    }
}
