package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponseMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetWorkingOnService.class)
@ActiveProfiles("test")
class GetWorkingOnServiceTest {

    @MockBean
    private GetWorkingOnPort getWorkingOnPort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;
    @MockBean
    private ResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;
    @Autowired
    private GetWorkingOnService getWorkingOnService;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOnMother.complete()
                .workingOnId(workingOnId)
                .build();
        List<WorkingOn> allRelations = new ArrayList<>();
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().build();
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn, allRelations)).thenReturn(getWorkingOnResponse);
        when(getWorkingOnPort.getWorkingOn(workingOnId)).thenReturn(workingOn);

        //when
        GetWorkingOnResponse response = getWorkingOnService.getWorkingOn(workingOnId);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn, allRelations);
        verify(getWorkingOnPort, times(1)).getWorkingOn(workingOnId);
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(workingOnResponseMapper);
        verifyNoMoreInteractions(getWorkingOnPort);
    }
}
