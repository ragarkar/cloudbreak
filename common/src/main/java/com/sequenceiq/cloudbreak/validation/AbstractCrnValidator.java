package com.sequenceiq.cloudbreak.validation;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.tuple.Pair;

import com.sequenceiq.cloudbreak.auth.crn.CrnResourceDescriptor;
import com.sequenceiq.common.api.util.ValidatorUtil;

public abstract class AbstractCrnValidator<T> implements ConstraintValidator<ValidCrn, T> {

    private CrnResourceDescriptor[] resourceDescriptors;

    private ValidCrn.Effect effect;

    @Override
    public void initialize(ValidCrn constraintAnnotation) {
        resourceDescriptors = constraintAnnotation.resource();
        effect = constraintAnnotation.effect();
    }

    @Override
    public boolean isValid(T req, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (crnInputIsEmpty(req)) {
            return true;
        }
        if (crnInputIsInvalid(req)) {
            String errorMessage = getInvalidCrnErrorMessage(req);
            ValidatorUtil.addConstraintViolation(constraintValidatorContext, errorMessage);
            return false;
        }
        if (resourceDescriptors.length != 0 && crnInputHasInvalidServiceOrResourceType(req, effect)) {
            Set<Pair> serviceAndResourceTypePairs = Arrays.stream(resourceDescriptors)
                    .map(CrnResourceDescriptor::createServiceAndResourceTypePair)
                    .collect(Collectors.toSet());
            String errorMessage = getErrorMessageIfServiceOrResourceTypeInvalid(req, serviceAndResourceTypePairs, effect);
            ValidatorUtil.addConstraintViolation(constraintValidatorContext, errorMessage);
            return false;
        }
        return true;
    }

    public CrnResourceDescriptor[] getResourceDescriptors() {
        return resourceDescriptors;
    }

    public ValidCrn.Effect getEffect() {
        return effect;
    }

    protected abstract String getErrorMessageIfServiceOrResourceTypeInvalid(T req, Set<Pair> serviceAndResourceTypePairs, ValidCrn.Effect effect);

    protected abstract boolean crnInputHasInvalidServiceOrResourceType(T req, ValidCrn.Effect effect);

    protected abstract String getInvalidCrnErrorMessage(T req);

    protected abstract boolean crnInputIsInvalid(T req);

    protected abstract boolean crnInputIsEmpty(T req);
}
