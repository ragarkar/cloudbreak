package com.sequenceiq.environment.configuration.api;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sequenceiq.sdx.client.internal.SdxApiClientParams;

@Configuration
public class SdxApiConfig {
    @Value("${rest.debug:false}")
    private boolean restDebug;

    @Value("${cert.validation:true}")
    private boolean certificateValidation;

    @Value("${cert.ignorePreValidation:true}")
    private boolean ignorePreValidation;

    @Inject
    @Named("sdxServerUrl")
    private String sdxServerUrl;

    @Bean
    public SdxApiClientParams sdxApiClientParams() {
        return new SdxApiClientParams(restDebug, certificateValidation, ignorePreValidation, sdxServerUrl);
    }
}
