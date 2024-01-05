package com.sequenceiq.datalake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sequenceiq.cloudbreak.util.OpenSSLLoaderUtil;

@EnableJpaRepositories(basePackages = { "com.sequenceiq" })
@SpringBootApplication(scanBasePackages = { "com.sequenceiq" }, exclude = ErrorMvcAutoConfiguration.class)
public class DatalakeApplication {

    public static void main(String[] args) {
        OpenSSLLoaderUtil.registerOpenSSLJniProvider();
        SpringApplication.run(DatalakeApplication.class, args);
    }

}

