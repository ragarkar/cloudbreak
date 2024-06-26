package com.sequenceiq.freeipa.flow.freeipa.cleanup.event.vault;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.freeipa.flow.freeipa.cleanup.CleanupEvent;
import com.sequenceiq.freeipa.flow.freeipa.cleanup.event.AbstractCleanupEvent;

public class RemoveVaultEntriesResponse extends AbstractCleanupEvent {

    private final Set<String> vaultCleanupSuccess;

    private final Map<String, String> vaultCleanupFailed;

    protected RemoveVaultEntriesResponse(Long stackId) {
        super(stackId);
        vaultCleanupSuccess = null;
        vaultCleanupFailed = null;
    }

    public RemoveVaultEntriesResponse(CleanupEvent cleanupEvent, Set<String> vaultCleanupSuccess,
            Map<String, String> vaultCleanupFailed) {
        super(cleanupEvent);
        this.vaultCleanupSuccess = vaultCleanupSuccess;
        this.vaultCleanupFailed = vaultCleanupFailed;
    }

    @JsonCreator
    public RemoveVaultEntriesResponse(
            @JsonProperty("selector") String selector,
            @JsonProperty("cleanupEvent") CleanupEvent cleanupEvent,
            @JsonProperty("vaultCleanupSuccess") Set<String> vaultCleanupSuccess,
            @JsonProperty("vaultCleanupFailed") Map<String, String> vaultCleanupFailed) {
        super(selector, cleanupEvent);
        this.vaultCleanupSuccess = vaultCleanupSuccess;
        this.vaultCleanupFailed = vaultCleanupFailed;
    }

    public Set<String> getVaultCleanupSuccess() {
        return vaultCleanupSuccess;
    }

    public Map<String, String> getVaultCleanupFailed() {
        return vaultCleanupFailed;
    }

    @Override
    public String toString() {
        return "RemoveVaultEntriesResponse{" +
                "vaultCleanupSuccess=" + vaultCleanupSuccess +
                ", vaultCleanupFailed=" + vaultCleanupFailed +
                '}';
    }
}
