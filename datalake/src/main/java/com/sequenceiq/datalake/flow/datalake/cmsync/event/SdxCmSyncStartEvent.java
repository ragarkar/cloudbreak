package com.sequenceiq.datalake.flow.datalake.cmsync.event;

import com.sequenceiq.datalake.flow.SdxEvent;

public class SdxCmSyncStartEvent extends SdxEvent {
    public SdxCmSyncStartEvent(Long sdxId, String userId) {
        super(sdxId, userId);
    }
}