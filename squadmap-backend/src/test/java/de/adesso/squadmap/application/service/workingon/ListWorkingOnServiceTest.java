package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponseMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.domain.mapper.ResponseMapper;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListWorkingOnServiceTest {

    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private ResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;
    @InjectMocks
    private ListWorkingOnService listWorkingOnService;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = WorkingOnMother.complete().workingOnId(1L).build();
        WorkingOn workingOn2 = WorkingOnMother.complete().workingOnId(2L).build();
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        GetWorkingOnResponse getWorkingOnResponse1 = GetWorkingOnResponseMother.complete().workingOnId(1L).build();
        GetWorkingOnResponse getWorkingOnResponse2 = GetWorkingOnResponseMother.complete().workingOnId(2L).build();
        List<GetWorkingOnResponse> getWorkingOnResponses = Arrays.asList(getWorkingOnResponse1, getWorkingOnResponse2);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn1, workingOns)).thenReturn(getWorkingOnResponse1);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn2, workingOns)).thenReturn(getWorkingOnResponse2);

        //when
        List<GetWorkingOnResponse> responses = listWorkingOnService.listWorkingOn();

        //then
        assertThat(responses).isEqualTo(getWorkingOnResponses);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn1, workingOns);
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn2, workingOns);
        verifyNoMoreInteractions(listWorkingOnPort, workingOnResponseMapper);
    }
}
