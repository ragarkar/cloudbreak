package com.sequenceiq.cloudbreak.rotation.secret;

public enum SecretRotationStep {
    VAULT,
    CM_USER,
    SALT_PILLAR,
    SALT_STATE_APPLY
}
