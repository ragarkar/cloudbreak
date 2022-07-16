package com.sequenceiq.cloudbreak.reactor.api.event.cluster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.reactor.api.ClusterPlatformRequest;

public class ClusterStopRequest extends ClusterPlatformRequest {
    @JsonCreator
    public ClusterStopRequest(
            @JsonProperty("stackId") Long stackId) {
        super(stackId);
    }
}
