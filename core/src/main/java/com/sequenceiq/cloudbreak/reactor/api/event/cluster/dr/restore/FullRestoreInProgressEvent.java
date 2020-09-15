package com.sequenceiq.cloudbreak.reactor.api.event.cluster.dr.restore;

import com.sequenceiq.cloudbreak.reactor.api.event.cluster.dr.BackupRestoreEvent;

public class FullRestoreInProgressEvent extends BackupRestoreEvent {

    public FullRestoreInProgressEvent(Long stackId, String backupId, String userCrn) {
        super(stackId, null, backupId, userCrn);
    }
}
