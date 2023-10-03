package com.sequenceiq.cloudbreak.rotation.common;

import com.sequenceiq.cloudbreak.rotation.SecretRotationStep;

public enum TestSecretRotationStep implements SecretRotationStep {
    STEP,
    STEP2,
    STEP3;

    @Override
    public Class<? extends Enum<?>> getClazz() {
        return TestSecretRotationStep.class;
    }

    @Override
    public String value() {
        return name();
    }
}
