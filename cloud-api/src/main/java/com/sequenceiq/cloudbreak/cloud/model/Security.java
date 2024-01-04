package com.sequenceiq.cloudbreak.cloud.model;

import java.util.Collection;
import java.util.List;

import jakarta.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class Security {

    private final List<SecurityRule> rules;

    private final List<String> cloudSecurityIds;

    private final boolean useNetworkCidrAsSourceForDefaultRules;

    public Security(@Nonnull Collection<SecurityRule> rules, @Nonnull Collection<String> cloudSecurityIds) {
        this(rules, cloudSecurityIds, false);
    }

    @JsonCreator
    public Security(
            @JsonProperty("rules") @Nonnull Collection<SecurityRule> rules,
            @JsonProperty("cloudSecurityIds") @Nonnull Collection<String> cloudSecurityIds,
            @JsonProperty("useNetworkCidrAsSourceForDefaultRules") boolean useNetworkCidrAsSourceForDefaultRules) {

        this.rules = ImmutableList.copyOf(rules);
        this.cloudSecurityIds = ImmutableList.copyOf(cloudSecurityIds);
        this.useNetworkCidrAsSourceForDefaultRules = useNetworkCidrAsSourceForDefaultRules;
    }

    public List<SecurityRule> getRules() {
        return rules;
    }

    public String getCloudSecurityId() {
        return cloudSecurityIds.isEmpty() ? null : cloudSecurityIds.get(0);
    }

    public List<String> getCloudSecurityIds() {
        return cloudSecurityIds;
    }

    public boolean isUseNetworkCidrAsSourceForDefaultRules() {
        return useNetworkCidrAsSourceForDefaultRules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Security{");
        sb.append("rules=").append(rules);
        sb.append("cloudSecurityIds=").append(cloudSecurityIds);
        sb.append('}');
        return sb.toString();
    }
}
