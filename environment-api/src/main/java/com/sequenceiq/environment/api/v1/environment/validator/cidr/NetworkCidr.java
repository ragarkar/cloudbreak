package com.sequenceiq.environment.api.v1.environment.validator.cidr;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NetworkCidrValidator.class)
public @interface NetworkCidr {
    String message() default "The format of the CIDR is not accepted. Prefix mask must be /16";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
