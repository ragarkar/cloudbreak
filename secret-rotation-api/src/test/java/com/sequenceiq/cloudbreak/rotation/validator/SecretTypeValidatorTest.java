package com.sequenceiq.cloudbreak.rotation.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.lang.annotation.Annotation;
import java.util.UUID;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.rotation.SecretType;
import com.sequenceiq.cloudbreak.rotation.annotation.ValidSecretType;
import com.sequenceiq.cloudbreak.rotation.common.TestSecretType;

@ExtendWith(MockitoExtension.class)
public class SecretTypeValidatorTest {

    private static final String USER_CRN = "crn:altus:iam:us-west-1:" + UUID.randomUUID() + ":user:" + UUID.randomUUID();

    private static final String INTERNAL_ACTOR_CRN = "crn:cdp:iam:us-west-1:altus:user:__internal__actor__";

    @Captor
    private ArgumentCaptor<String> errorMessageCaptor;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeEach
    void setup() {
        setupContext();
    }

    @Test
    void testValidateIfInvalidType() {
        assertFalse(ThreadBasedUserCrnProvider.doAs(USER_CRN, () -> validator().isValid("INVALID", context)));
        assertEquals("Invalid secret type, cannot map secret INVALID.", errorMessageCaptor.getValue());
    }

    @Test
    void testValidateIfInternalNotAllowed() {
        assertFalse(ThreadBasedUserCrnProvider.doAs(USER_CRN, () -> validator().isValid("TEST_3", context)));
        assertEquals("Internal secret types can be rotated only by using internal actor!", errorMessageCaptor.getValue());
    }

    @Test
    void testValidate() {
        assertTrue(ThreadBasedUserCrnProvider.doAs(INTERNAL_ACTOR_CRN, () -> validator().isValid("TEST_3", context)));
    }

    private SecretTypeValidator validator() {
        SecretTypeValidator validator = new SecretTypeValidator();
        validator.initialize(new ValidSecretType() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public Class<? extends SecretType>[] allowedTypes() {
                return new Class[] { TestSecretType.class };
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }
        });
        return validator;
    }

    private void setupContext() {
        lenient().doReturn(builder).when(context).buildConstraintViolationWithTemplate(errorMessageCaptor.capture());
        lenient().doReturn(context).when(builder).addConstraintViolation();
    }
}
