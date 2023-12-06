package com.sequenceiq.cloudbreak.rotation.executor;

import static com.sequenceiq.freeipa.rotation.FreeIpaSecretType.FREEIPA_LDAP_BIND_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.dto.StackDto;
import com.sequenceiq.cloudbreak.rotation.RotationFlowExecutionType;
import com.sequenceiq.cloudbreak.rotation.RotationMetadataTestUtil;
import com.sequenceiq.cloudbreak.rotation.common.SecretRotationException;
import com.sequenceiq.cloudbreak.rotation.secret.poller.PollerRotationContext;
import com.sequenceiq.cloudbreak.rotation.service.notification.SecretRotationNotificationService;
import com.sequenceiq.cloudbreak.service.freeipa.FreeipaService;
import com.sequenceiq.cloudbreak.service.stack.StackDtoService;

@ExtendWith(MockitoExtension.class)
class FreeIpaPollerRotationExecutorTest {

    private static final String RESOURCE_CRN = "resourceCrn";

    private static final String ENVIRONMENT_CRN = "environmentCrn";

    private static final String CLUSTER_NAME = "clusterName";

    @Mock
    private StackDtoService stackDtoService;

    @Mock
    private FreeipaService freeipaService;

    @Mock
    private SecretRotationNotificationService secretRotationNotificationService;

    @InjectMocks
    private FreeIpaPollerRotationExecutor underTest;

    @Test
    void rotateShouldSucceed() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);

        underTest.executeRotate(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD), null);

        verify(stackDtoService, times(1)).getByCrn(eq(RESOURCE_CRN));
        verify(freeipaService, times(1)).rotateFreeIpaSecret(eq(ENVIRONMENT_CRN),
                eq(CLUSTER_NAME), eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.ROTATE));
    }

    @Test
    void rollbackShouldSucceed() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);

        underTest.executeRollback(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD), null);

        verify(stackDtoService, times(1)).getByCrn(eq(RESOURCE_CRN));
        verify(freeipaService, times(1)).rotateFreeIpaSecret(eq(ENVIRONMENT_CRN),
                eq(CLUSTER_NAME), eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.ROLLBACK));
    }

    @Test
    void finalizeShouldSucceed() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);

        underTest.executeFinalize(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD), null);

        verify(stackDtoService, times(1)).getByCrn(eq(RESOURCE_CRN));
        verify(freeipaService, times(1)).rotateFreeIpaSecret(eq(ENVIRONMENT_CRN),
                eq(CLUSTER_NAME), eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.FINALIZE));
    }

    @Test
    void preValidateShouldSucceed() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);

        underTest.preValidate(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD));
        verify(stackDtoService, times(1)).getByCrn(eq(RESOURCE_CRN));
        verify(freeipaService, times(1)).preValidateFreeIpaSecretRotation(eq(ENVIRONMENT_CRN));
    }

    @Test
    void rotateShouldThrowSecretRotationExceptionIfExternalCallFails() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);
        doThrow(new RuntimeException("error")).when(freeipaService)
                .rotateFreeIpaSecret(eq(ENVIRONMENT_CRN), eq(CLUSTER_NAME),
                        eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.ROTATE));

        SecretRotationException secretRotationException = assertThrows(SecretRotationException.class,
                () -> underTest.executeRotate(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD),
                        RotationMetadataTestUtil.metadataForRotation(RESOURCE_CRN, null)));

        assertEquals("Execution of rotation failed at FREEIPA_ROTATE_POLLING step for resourceCrn regarding secret null, reason: error",
                secretRotationException.getMessage());
    }

    @Test
    void rollbackShouldThrowSecretRotationExceptionIfExternalCallFails() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);
        doThrow(new RuntimeException("error")).when(freeipaService)
                .rotateFreeIpaSecret(eq(ENVIRONMENT_CRN), eq(CLUSTER_NAME),
                        eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.ROLLBACK));

        SecretRotationException secretRotationException = assertThrows(SecretRotationException.class,
                () -> underTest.executeRollback(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD),
                        RotationMetadataTestUtil.metadataForRollback(RESOURCE_CRN, null)));

        assertEquals("Rollback of rotation failed at FREEIPA_ROTATE_POLLING step for resourceCrn regarding secret null, reason: error",
                secretRotationException.getMessage());
    }

    @Test
    void finalizeShouldThrowSecretRotationExceptionIfExternalCallFails() {
        StackDto stackDto = mock(StackDto.class);
        when(stackDtoService.getByCrn(RESOURCE_CRN)).thenReturn(stackDto);
        when(stackDto.getEnvironmentCrn()).thenReturn(ENVIRONMENT_CRN);
        when(stackDto.getName()).thenReturn(CLUSTER_NAME);
        doThrow(new RuntimeException("error")).when(freeipaService)
                .rotateFreeIpaSecret(eq(ENVIRONMENT_CRN), eq(CLUSTER_NAME),
                        eq(FREEIPA_LDAP_BIND_PASSWORD), eq(RotationFlowExecutionType.FINALIZE));

        SecretRotationException secretRotationException = assertThrows(SecretRotationException.class,
                () -> underTest.executeFinalize(new PollerRotationContext(RESOURCE_CRN, FREEIPA_LDAP_BIND_PASSWORD),
                        RotationMetadataTestUtil.metadataForFinalize(RESOURCE_CRN, null)));

        assertEquals("Finalization of rotation failed at FREEIPA_ROTATE_POLLING step for resourceCrn regarding secret null, reason: error",
                secretRotationException.getMessage());
    }

}