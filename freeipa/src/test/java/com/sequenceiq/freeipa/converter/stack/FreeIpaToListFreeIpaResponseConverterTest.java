package com.sequenceiq.freeipa.converter.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.freeipa.api.v1.freeipa.stack.model.common.AvailabilityStatus;
import com.sequenceiq.freeipa.api.v1.freeipa.stack.model.common.Status;
import com.sequenceiq.freeipa.api.v1.freeipa.stack.model.list.ListFreeIpaResponse;
import com.sequenceiq.freeipa.entity.StackStatus;
import com.sequenceiq.freeipa.entity.projection.FreeIpaListView;

@ExtendWith(MockitoExtension.class)
class FreeIpaToListFreeIpaResponseConverterTest {

    private static final String ENV_CRN = "envCrn";

    private static final String NAME = "freeIpa";

    private static final String DOMAIN_1 = "dom1";

    private static final String DOMAIN_2 = "dom2";

    private static final String CRN_1 = "crn1";

    private static final String CRN_2 = "crn2";

    @InjectMocks
    private FreeIpaToListFreeIpaResponseConverter underTest;

    @Mock
    private StackToAvailabilityStatusConverter stackToAvailabilityStatusConverter;

    @Test
    void testConvertList() {
        List<FreeIpaListView> freeIpaList = createFreeIpaList();

        when(stackToAvailabilityStatusConverter.convert(any())).thenReturn(AvailabilityStatus.AVAILABLE);

        List<ListFreeIpaResponse> actual = underTest.convertList(freeIpaList);

        assertEquals(DOMAIN_1, actual.get(0).getDomain());
        assertEquals(CRN_1, actual.get(0).getCrn());
        assertEquals(ENV_CRN, actual.get(0).getEnvironmentCrn());
        assertEquals(NAME, actual.get(0).getName());
        assertEquals(Status.AVAILABLE, actual.get(0).getStatus());

        assertEquals(DOMAIN_2, actual.get(1).getDomain());
        assertEquals(CRN_2, actual.get(1).getCrn());
        assertEquals(ENV_CRN, actual.get(1).getEnvironmentCrn());
        assertEquals(NAME, actual.get(1).getName());
        assertEquals(Status.AVAILABLE, actual.get(1).getStatus());
    }

    private List<FreeIpaListView> createFreeIpaList() {
        return List.of(createFreeIpa(DOMAIN_1, CRN_1), createFreeIpa(DOMAIN_2, CRN_2));
    }

    private FreeIpaListView createFreeIpa(String domain, String crn) {
        return new FreeIpaListView(domain, NAME, crn, ENV_CRN, createStackStatus());
    }

    private StackStatus createStackStatus() {
        StackStatus stackStatus = new StackStatus();
        stackStatus.setStatus(Status.AVAILABLE);
        return stackStatus;
    }

}