package com.sequenceiq.cloudbreak.rotation.flow.rotation.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.rotation.RotationFlowExecutionType;
import com.sequenceiq.cloudbreak.rotation.SecretType;
import com.sequenceiq.flow.event.EventSelectorUtil;

public class RotationFailedEvent extends RotationEvent {

    private final Exception exception;

    @JsonCreator
    public RotationFailedEvent(@JsonProperty("selector") String selector,
            @JsonProperty("resourceId") Long resourceId,
            @JsonProperty("resourceCrn") String resourceCrn,
            @JsonProperty("secretType") SecretType secretType,
            @JsonProperty("executionType") RotationFlowExecutionType executionType,
            @JsonProperty("exception") Exception exception) {
        super(selector, resourceId, resourceCrn, secretType, executionType);
        this.exception = exception;
    }

    public static RotationFailedEvent fromPayload(RotationEvent payload, Exception ex) {
        return new RotationFailedEvent(EventSelectorUtil.selector(RotationFailedEvent.class), payload.getResourceId(), payload.getResourceCrn(),
                payload.getSecretType(), payload.getExecutionType(), ex);
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "RotationFailedEvent{" +
                "exception=" + exception +
                "} " + super.toString();
    }
}
