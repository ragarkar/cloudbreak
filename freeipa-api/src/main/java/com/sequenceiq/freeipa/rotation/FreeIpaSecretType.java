package com.sequenceiq.freeipa.rotation;

import static com.sequenceiq.cloudbreak.rotation.CommonSecretRotationStep.CUSTOM_JOB;
import static com.sequenceiq.cloudbreak.rotation.CommonSecretRotationStep.SALTBOOT_CONFIG;
import static com.sequenceiq.cloudbreak.rotation.CommonSecretRotationStep.USER_DATA;
import static com.sequenceiq.cloudbreak.rotation.CommonSecretRotationStep.VAULT;
import static com.sequenceiq.cloudbreak.rotation.MultiSecretType.DEMO_MULTI_SECRET;
import static com.sequenceiq.cloudbreak.rotation.SecretTypeFlag.INTERNAL;
import static com.sequenceiq.cloudbreak.rotation.SecretTypeFlag.SKIP_SALT_UPDATE;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.CCMV2_JUMPGATE;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.FREEIPA_ADMIN_USER_PASSWORD;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.FREEIPA_DIRECTORY_MANAGER_PASSWORD;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.LAUNCH_TEMPLATE;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.SALT_PILLAR_UPDATE;
import static com.sequenceiq.freeipa.rotation.FreeIpaSecretRotationStep.SALT_STATE_APPLY;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sequenceiq.cloudbreak.rotation.MultiSecretType;
import com.sequenceiq.cloudbreak.rotation.SecretRotationStep;
import com.sequenceiq.cloudbreak.rotation.SecretType;
import com.sequenceiq.cloudbreak.rotation.SecretTypeFlag;

public enum FreeIpaSecretType implements SecretType {
    FREEIPA_ADMIN_PASSWORD(List.of(VAULT, FREEIPA_ADMIN_USER_PASSWORD, FREEIPA_DIRECTORY_MANAGER_PASSWORD, SALT_PILLAR_UPDATE)),
    FREEIPA_SALT_BOOT_SECRETS(List.of(VAULT, CUSTOM_JOB, SALTBOOT_CONFIG, USER_DATA, LAUNCH_TEMPLATE)),
    CCMV2_JUMPGATE_AGENT_ACCESS_KEY(List.of(CCMV2_JUMPGATE, LAUNCH_TEMPLATE, SALT_PILLAR_UPDATE, SALT_STATE_APPLY)),
    FREEIPA_DEMO_SECRET(List.of(CUSTOM_JOB), DEMO_MULTI_SECRET, Set.of(SKIP_SALT_UPDATE, INTERNAL));

    private final List<SecretRotationStep> steps;

    private final Optional<MultiSecretType> multiSecretType;

    private final Set<SecretTypeFlag> flags;

    FreeIpaSecretType(List<SecretRotationStep> steps) {
        this.steps = steps;
        this.multiSecretType = Optional.empty();
        this.flags = Set.of();
    }

    FreeIpaSecretType(List<SecretRotationStep> steps, MultiSecretType multiSecretType, Set<SecretTypeFlag> flags) {
        this.steps = steps;
        this.multiSecretType = Optional.ofNullable(multiSecretType);
        this.flags = flags;
    }

    @Override
    public List<SecretRotationStep> getSteps() {
        return steps;
    }

    @Override
    public Set<SecretTypeFlag> getFlags() {
        return flags;
    }

    @Override
    public Optional<MultiSecretType> getMultiSecretType() {
        return multiSecretType;
    }

    @Override
    public Class<? extends Enum<?>> getClazz() {
        return FreeIpaSecretType.class;
    }

    @Override
    public String value() {
        return name();
    }
}
