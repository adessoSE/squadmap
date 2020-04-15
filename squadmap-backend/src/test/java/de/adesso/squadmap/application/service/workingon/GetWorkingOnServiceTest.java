package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponseMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.domain.mapper.ResponseMapper;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GetWorkingOnServiceTest {

    @Mock
    private GetWorkingOnPort getWorkingOnPort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private ResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;
    @InjectMocks
    private GetWorkingOnService getWorkingOnService;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOnMother.complete().workingOnId(workingOnId).build();
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().workingOnId(workingOnId).build();
        List<WorkingOn> workingOns = Collections.emptyList();
        when(getWorkingOnPort.getWorkingOn(workingOnId)).thenReturn(workingOn);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn, workingOns)).thenReturn(getWorkingOnResponse);

        //when
        GetWorkingOnResponse response = getWorkingOnService.getWorkingOn(workingOnId);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(getWorkingOnPort, times(1)).getWorkingOn(workingOnId);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn, workingOns);
        verifyNoMoreInteractions(getWorkingOnPort, listWorkingOnPort, workingOnResponseMapper);
    }
}
