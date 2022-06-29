package com.sequenceiq.datalake.service.sdx.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.common.service.Clock;
import com.sequenceiq.cloudbreak.common.service.TransactionService;
import com.sequenceiq.cloudbreak.event.ResourceEvent;
import com.sequenceiq.datalake.entity.DatalakeStatusEnum;
import com.sequenceiq.datalake.entity.SdxCluster;
import com.sequenceiq.datalake.entity.SdxStatusEntity;
import com.sequenceiq.datalake.repository.SdxClusterRepository;
import com.sequenceiq.datalake.repository.SdxStatusRepository;
import com.sequenceiq.datalake.service.sdx.SdxNotificationService;

@ExtendWith(MockitoExtension.class)
class SdxStatusServiceTest {

    private static final Long TIMESTAMP = 1000L;

    @Mock
    private TransactionService transactionService;

    @Mock
    private SdxNotificationService sdxNotificationService;

    @Mock
    private SdxStatusRepository sdxStatusRepository;

    @Mock
    private SdxClusterRepository sdxClusterRepository;

    @Spy
    private Clock clock;

    @InjectMocks
    private SdxStatusService sdxStatusService;

    @Captor
    private ArgumentCaptor<SdxCluster> sdxClusterCaptor;

    @Captor
    private ArgumentCaptor<SdxStatusEntity> statusEntityCaptor;

    private SdxCluster sdxCluster;

    private SdxStatusEntity oldStatus;

    @BeforeEach
    void setUp() throws TransactionService.TransactionExecutionException {
        doAnswer(invocation -> {
            invocation.getArgument(0, Runnable.class).run();
            return null;
        }).when(transactionService).required(any(Runnable.class));

        sdxCluster = new SdxCluster();
        sdxCluster.setRuntime("7.0.2");
        sdxCluster.setClusterName("datalake-cluster");
        sdxCluster.setId(2L);
        when(sdxClusterRepository.findById(eq(2L))).thenReturn(Optional.of(sdxCluster));

        oldStatus = new SdxStatusEntity();
        oldStatus.setCreated(1L);
        oldStatus.setStatusReason("stack deleted");
        oldStatus.setId(1L);
        oldStatus.setDatalake(sdxCluster);
        when(sdxStatusRepository.findFirstByDatalakeIsOrderByIdDesc(any(SdxCluster.class))).thenReturn(oldStatus);

        lenient().when(clock.getCurrentTimeMillis()).thenReturn(TIMESTAMP);
        lenient().when(sdxClusterRepository.save(sdxClusterCaptor.capture())).thenReturn(sdxCluster);
        lenient().when(sdxStatusRepository.save(statusEntityCaptor.capture())).thenReturn(null);
    }

    @Test
    void setStatusForDatalakeAndNotify() throws Exception {
        oldStatus.setStatus(DatalakeStatusEnum.STACK_DELETED);
        DatalakeStatusEnum status = DatalakeStatusEnum.DELETED;
        ResourceEvent resourceEvent = ResourceEvent.SDX_RDS_DELETION_FINISHED;

        sdxStatusService.setStatusForDatalakeAndNotify(status, resourceEvent, "deleted", sdxCluster);

        verify(sdxStatusRepository).save(any(SdxStatusEntity.class));
        assertEquals(status, statusEntityCaptor.getValue().getStatus());
        assertEquals(TIMESTAMP, sdxClusterCaptor.getValue().getDeleted());
        verify(transactionService).required(any(Runnable.class));
        verify(sdxNotificationService).send(resourceEvent, sdxCluster);
    }

    @Test
    void setStatusForDatalakeAndNotifyWithArgs() throws Exception {
        oldStatus.setStatus(DatalakeStatusEnum.RUNNING);
        DatalakeStatusEnum status = DatalakeStatusEnum.SALT_PASSWORD_ROTATION_FAILED;
        Set<String> messageArgs = Collections.singleton("exception-message");

        sdxStatusService.setStatusForDatalakeAndNotify(status, messageArgs, "Rotating SaltStack user password failed", sdxCluster.getId());

        verify(sdxStatusRepository).save(any(SdxStatusEntity.class));
        assertEquals(status, statusEntityCaptor.getValue().getStatus());
        verify(transactionService).required(any(Runnable.class));
        verify(sdxNotificationService).send(status.getDefaultResourceEvent(), messageArgs, sdxCluster);
    }
}
