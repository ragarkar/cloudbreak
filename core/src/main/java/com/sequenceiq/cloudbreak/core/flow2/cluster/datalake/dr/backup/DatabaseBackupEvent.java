package com.sequenceiq.cloudbreak.core.flow2.cluster.datalake.dr.backup;

import com.sequenceiq.cloudbreak.reactor.api.event.cluster.dr.backup.DatalakeBackupFailedEvent;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.dr.backup.DatalakeBackupSuccess;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.dr.backup.FullBackupInProgressEvent;
import com.sequenceiq.flow.core.FlowEvent;
import com.sequenceiq.flow.event.EventSelectorUtil;

public enum DatabaseBackupEvent implements FlowEvent {
    DATABASE_BACKUP_IN_PROGRESS_EVENT("DATABASE_BACKUP_IN_PROGRESS_EVENT"),
    DATABASE_BACKUP_FAILED_EVENT(EventSelectorUtil.selector(DatalakeBackupFailedEvent.class)),
    FULL_BACKUP_IN_PROGRESS_EVENT(EventSelectorUtil.selector(FullBackupInProgressEvent.class)),
    BACKUP_FINISHED_EVENT(EventSelectorUtil.selector(DatalakeBackupSuccess.class)),
    BACKUP_FINALIZED_EVENT("BACKUP_FINALIZED_EVENT"),
    BACKUP_FAIL_HANDLED_EVENT("BACKUP_FAIL_HANDLED_EVENT");

    private final String event;

    DatabaseBackupEvent(String event) {
        this.event = event;
    }

    @Override
    public String event() {
        return event;
    }
}
