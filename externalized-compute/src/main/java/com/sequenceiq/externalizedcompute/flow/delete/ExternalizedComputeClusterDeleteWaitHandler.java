package com.sequenceiq.externalizedcompute.flow.delete;

import java.util.concurrent.TimeUnit;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudera.thunderhead.service.liftiepublic.LiftiePublicProto.DescribeClusterResponse;
import com.dyngr.Polling;
import com.dyngr.core.AttemptResults;
import com.dyngr.exception.PollerStoppedException;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.cloudbreak.eventbus.Event;
import com.sequenceiq.externalizedcompute.entity.ExternalizedComputeCluster;
import com.sequenceiq.externalizedcompute.flow.create.ExternalizedComputeClusterCreateFailedEvent;
import com.sequenceiq.externalizedcompute.service.ExternalizedComputeClusterService;
import com.sequenceiq.flow.event.EventSelectorUtil;
import com.sequenceiq.flow.reactor.api.handler.ExceptionCatcherEventHandler;
import com.sequenceiq.flow.reactor.api.handler.HandlerEvent;
import com.sequenceiq.liftie.client.LiftieGrpcClient;

import io.grpc.StatusRuntimeException;

@Component
public class ExternalizedComputeClusterDeleteWaitHandler extends ExceptionCatcherEventHandler<ExternalizedComputeClusterDeleteWaitRequest> {

    public static final String DELETED_STATUS = "DELETED";

    public static final String DELETE_FAILED_STATUS = "DELETE_FAILED";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalizedComputeClusterDeleteWaitHandler.class);

    @Value("${externalizedcompute.delete.wait.sleep.time.sec:10}")
    private int sleepTime;

    @Value("${externalizedcompute.delete.wait.sleep.time.limit.hour:2}")
    private int timeLimit;

    @Inject
    private ExternalizedComputeClusterService externalizedComputeClusterService;

    @Inject
    private LiftieGrpcClient liftieGrpcClient;

    @Override
    protected Selectable defaultFailureEvent(Long resourceId, Exception e, Event<ExternalizedComputeClusterDeleteWaitRequest> event) {
        return new ExternalizedComputeClusterDeleteFailedEvent(resourceId, event.getData().getActorCrn(), e);
    }

    @Override
    protected Selectable doAccept(HandlerEvent<ExternalizedComputeClusterDeleteWaitRequest> event) {
        Long resourceId = event.getData().getResourceId();
        ExternalizedComputeCluster externalizedComputeCluster =
                externalizedComputeClusterService.getExternalizedComputeCluster(resourceId);
        String actorCrn = event.getData().getActorCrn();
        if (externalizedComputeCluster.getLiftieName() == null) {
            LOGGER.info("Liftie cluster does not exists, so we can skip waiting");
            return new ExternalizedComputeClusterDeleteWaitSuccessResponse(resourceId, actorCrn);
        }
        try {
            return Polling.stopAfterDelay(timeLimit, TimeUnit.HOURS)
                    .stopIfException(false)
                    .waitPeriodly(sleepTime, TimeUnit.SECONDS)
                    .run(() -> {
                        try {
                            DescribeClusterResponse cluster = liftieGrpcClient.describeCluster(
                                    externalizedComputeClusterService.getLiftieClusterCrn(externalizedComputeCluster), actorCrn);
                            LOGGER.debug("Cluster status: {}", cluster.getStatus());
                            switch (cluster.getStatus()) {
                                case DELETED_STATUS -> {
                                    LOGGER.debug("Cluster deleted successfully");
                                    return AttemptResults.finishWith(new ExternalizedComputeClusterDeleteWaitSuccessResponse(resourceId, actorCrn));
                                }
                                case DELETE_FAILED_STATUS -> {
                                    LOGGER.error("Cluster status is a failed status: {}", cluster.getStatus());
                                    String errorMessage = cluster.getMessage();
                                    return AttemptResults.finishWith(new ExternalizedComputeClusterDeleteFailedEvent(resourceId, actorCrn,
                                            new RuntimeException("Cluster deletion failed. Status: " + cluster.getStatus() + ". Message: " + errorMessage)));
                                }
                                default -> {
                                    LOGGER.info("Neither \"DELETED\" status nor \"DELETE_FAILED\" status: {}", cluster.getStatus());
                                    return AttemptResults.justContinue();
                                }
                            }
                        } catch (StatusRuntimeException statusRuntimeException) {
                            LOGGER.warn("'{}' liftie cluster describe failed", externalizedComputeCluster.getLiftieName(), statusRuntimeException);
                            if (statusRuntimeException.getMessage().contains("not found in database")) {
                                return AttemptResults.finishWith(new ExternalizedComputeClusterDeleteWaitSuccessResponse(resourceId, actorCrn));
                            } else {
                                return AttemptResults.justContinue();
                            }
                        }
                    });
        } catch (PollerStoppedException e) {
            DescribeClusterResponse cluster =
                    liftieGrpcClient.describeCluster(externalizedComputeClusterService.getLiftieClusterCrn(externalizedComputeCluster), actorCrn);
            LOGGER.warn("Liftie cluster deletion timed out: {}. Cluster response: {}", externalizedComputeCluster.getLiftieName(), cluster,  e);
            return new ExternalizedComputeClusterCreateFailedEvent(resourceId, actorCrn, new RuntimeException("Compute cluster deletion timed out. " +
                    "The last known status is:" + cluster.getStatus() + ". Message: " + cluster.getMessage()));
        }
    }

    @Override
    public String selector() {
        return EventSelectorUtil.selector(ExternalizedComputeClusterDeleteWaitRequest.class);
    }
}
