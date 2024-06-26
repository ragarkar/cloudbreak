package com.sequenceiq.periscope.monitor.evaluator;

import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sequenceiq.periscope.monitor.context.ClusterIdEvaluatorContext;
import com.sequenceiq.periscope.monitor.context.EvaluatorContext;
import com.sequenceiq.periscope.monitor.event.ClusterStatusSyncEvent;

@Component("ClusterStateEvaluator")
@Scope("prototype")
public class ClusterStateEvaluator extends EvaluatorExecutor {

    private static final String EVALUATOR_NAME = ClusterStateEvaluator.class.getName();

    @Inject
    private EventPublisher eventPublisher;

    private long clusterId;

    @Override
    public void setContext(EvaluatorContext context) {
        clusterId = (long) context.getData();
    }

    @Override
    @Nonnull
    public EvaluatorContext getContext() {
        return new ClusterIdEvaluatorContext(clusterId);
    }

    @Override
    public String getName() {
        return EVALUATOR_NAME;
    }

    @Override
    public void execute() {
        eventPublisher.publishEvent(new ClusterStatusSyncEvent(clusterId));
    }
}
