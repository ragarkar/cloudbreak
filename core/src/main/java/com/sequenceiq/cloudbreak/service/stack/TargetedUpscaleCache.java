package com.sequenceiq.cloudbreak.service.stack;

import java.util.concurrent.TimeUnit;

import com.sequenceiq.cloudbreak.cache.common.AbstractCacheDefinition;

public class TargetedUpscaleCache  extends AbstractCacheDefinition {

    private static final long MAX_ENTRIES = 1000L;

    private static final long TTL_MINUTES = 5L;

    @Override
    protected String getName() {
        return "targetedUpscaleCache";
    }

    @Override
    protected long getMaxEntries() {
        return MAX_ENTRIES;
    }

    @Override
    protected long getTimeToLiveSeconds() {
        return TimeUnit.MINUTES.toSeconds(TTL_MINUTES);
    }
}
