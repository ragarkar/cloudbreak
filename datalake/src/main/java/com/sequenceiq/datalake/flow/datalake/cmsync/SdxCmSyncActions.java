package com.sequenceiq.datalake.flow.datalake.cmsync;

import static com.sequenceiq.datalake.flow.datalake.cmsync.SdxCmSyncEvent.SDX_CM_SYNC_FAILED_HANDLED_EVENT;
import static com.sequenceiq.datalake.flow.datalake.cmsync.SdxCmSyncEvent.SDX_CM_SYNC_FINALIZED_EVENT;
import static com.sequenceiq.datalake.flow.datalake.cmsync.SdxCmSyncEvent.SDX_CM_SYNC_IN_PROGRESS_EVENT;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import com.sequenceiq.datalake.flow.SdxContext;
import com.sequenceiq.datalake.flow.SdxEvent;
import com.sequenceiq.datalake.flow.datalake.cmsync.event.SdxCmSyncFailedEvent;
import com.sequenceiq.datalake.flow.datalake.cmsync.event.SdxCmSyncStartEvent;
import com.sequenceiq.datalake.flow.datalake.cmsync.event.SdxCmSyncWaitEvent;
import com.sequenceiq.datalake.service.AbstractSdxAction;
import com.sequenceiq.datalake.service.sdx.SdxCmSyncService;
import com.sequenceiq.datalake.service.sdx.SdxUpgradeService;
import com.sequenceiq.flow.core.FlowEvent;
import com.sequenceiq.flow.core.FlowParameters;
import com.sequenceiq.flow.core.FlowState;

@Configuration
public class SdxCmSyncActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(SdxCmSyncActions.class);

    @Inject
    private SdxCmSyncService sdxCmSyncService;

    @Inject
    private SdxUpgradeService sdxUpgradeService;

    @Bean(name = "CORE_CM_SYNC_STATE")
    public Action<?, ?> callCoreCmSync() {
        return new AbstractSdxCmSyncAction<>(SdxCmSyncStartEvent.class) {

            @Override
            protected void doExecute(SdxContext context, SdxCmSyncStartEvent payload, Map<Object, Object> variables) throws Exception {
                LOGGER.debug("Calling cm sync on core for id: {}, name: {}", payload.getResourceId(), payload.getSdxName());
                sdxCmSyncService.callCmSync(payload.getResourceId());
                sendEvent(context, new SdxEvent(SDX_CM_SYNC_IN_PROGRESS_EVENT.event(), context));
            }
        };
    }

    @Bean(name = "CORE_CM_SYNC_IN_PROGRESS_STATE")
    public Action<?, ?> waitForCoreCmSync() {
        return new AbstractSdxCmSyncAction<>(SdxEvent.class) {

            @Override
            protected void doExecute(SdxContext context, SdxEvent payload, Map<Object, Object> variables) {
                SdxCmSyncWaitEvent sdxCmSyncWaitEvent = new SdxCmSyncWaitEvent(context);
                sendEvent(context, sdxCmSyncWaitEvent);
            }
        };
    }

    @Bean(name = "SDX_CM_SYNC_FINISHED_STATE")
    public Action<?, ?> sdxCmSyncFinished() {
        return new AbstractSdxCmSyncAction<>(SdxEvent.class) {

            @Override
            protected void doExecute(SdxContext context, SdxEvent payload, Map<Object, Object> variables) {
                Optional<String> newVersionOpt = sdxUpgradeService.updateRuntimeVersionFromCloudbreak(payload.getResourceId());
                LOGGER.info("Sdx cm sync updated sdx version, id: {}, new version: {}", payload.getResourceId(), newVersionOpt);
                sendEvent(context, SDX_CM_SYNC_FINALIZED_EVENT.event(), payload);
            }
        };
    }

    @Bean(name = "SDX_CM_SYNC_FAILED_STATE")
    public Action<?, ?> cmSyncFailedAction() {
        return new AbstractSdxAction<>(SdxCmSyncFailedEvent.class) {

            @Override
            protected SdxContext createFlowContext(FlowParameters flowParameters, StateContext<FlowState, FlowEvent> stateContext,
                    SdxCmSyncFailedEvent payload) {
                return SdxContext.from(flowParameters, payload);
            }

            @Override
            protected void doExecute(SdxContext context, SdxCmSyncFailedEvent payload, Map<Object, Object> variables) throws Exception {
                LOGGER.info("Sdx cm sync failure, id: {}, error: {}", payload.getResourceId(), payload.getException());
                sendEvent(context, SDX_CM_SYNC_FAILED_HANDLED_EVENT.event(), payload);
            }

            @Override
            protected Object getFailurePayload(SdxCmSyncFailedEvent payload, Optional<SdxContext> flowContext, Exception ex) {
                return null;
            }
        };
    }

}
