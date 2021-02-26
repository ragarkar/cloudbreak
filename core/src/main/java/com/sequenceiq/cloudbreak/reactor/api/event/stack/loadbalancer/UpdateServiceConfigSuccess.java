package com.sequenceiq.cloudbreak.reactor.api.event.stack.loadbalancer;

import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.reactor.api.event.StackEvent;

public class UpdateServiceConfigSuccess extends StackEvent {

    private final Stack stack;

    public UpdateServiceConfigSuccess(Stack stack) {
        super(stack.getId());
        this.stack = stack;
    }

    public Stack getStack() {
        return stack;
    }
}