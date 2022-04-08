package com.sequenceiq.cloudbreak.telemetry.monitoring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("telemetry.monitoring")
public class MonitoringConfiguration {

    private boolean enabled;

    private String remoteWriteUrl;

    private Integer scrapeIntervalSeconds;

    private boolean devStack;

    private boolean paasSupport;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemoteWriteUrl() {
        return remoteWriteUrl;
    }

    public void setRemoteWriteUrl(String remoteWriteUrl) {
        this.remoteWriteUrl = remoteWriteUrl;
    }

    public boolean isDevStack() {
        return devStack;
    }

    public void setDevStack(boolean devStack) {
        this.devStack = devStack;
    }

    public Integer getScrapeIntervalSeconds() {
        return scrapeIntervalSeconds;
    }

    public void setScrapeIntervalSeconds(Integer scrapeIntervalSeconds) {
        this.scrapeIntervalSeconds = scrapeIntervalSeconds;
    }

    public boolean isPaasSupport() {
        return paasSupport;
    }

    public void setPaasSupport(boolean paasSupport) {
        this.paasSupport = paasSupport;
    }
}
