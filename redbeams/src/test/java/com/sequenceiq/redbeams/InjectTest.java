package com.sequenceiq.redbeams;

import org.junit.jupiter.api.Test;

import com.sequenceiq.cloudbreak.util.UnusedInjectChecker;

public class InjectTest {

    @Test
    public void testIfThereAreUnusedInjections() {
        new UnusedInjectChecker().check();
    }

}
