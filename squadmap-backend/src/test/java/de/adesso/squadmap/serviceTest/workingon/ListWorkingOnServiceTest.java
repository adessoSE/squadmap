package de.adesso.squadmap.serviceTest.workingon;

import de.adesso.squadmap.adapter.workingon.ListWorkingOnAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.service.workingon.ListWorkingOnService;
import de.adesso.squadmap.utility.WorkingOnToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ListWorkingOnServiceTest {

    @Autowired
    private ListWorkingOnService service;
    @MockBean
    private ListWorkingOnAdapter listWorkingOnAdapter;
    @MockBean
    private WorkingOnToResponseMapper workingOnMapper;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = new WorkingOn();
        workingOn1.setWorkingOnId(1L);
        WorkingOn workingOn2 = new WorkingOn();
        workingOn2.setWorkingOnId(2L);
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        GetWorkingOnResponse response1 = new GetWorkingOnResponse();
        response1.setWorkingOnId(1L);
        GetWorkingOnResponse response2 = new GetWorkingOnResponse();
        response2.setWorkingOnId(2L);
        List<GetWorkingOnResponse> getWorkingOnResponses = Arrays.asList(response1, response2);
        when(listWorkingOnAdapter.listWorkingOn()).thenReturn(workingOns);
        when(workingOnMapper.map(workingOn1)).thenReturn(response1);
        when(workingOnMapper.map(workingOn2)).thenReturn(response2);

        //when
        List<GetWorkingOnResponse> responses = service.listWorkingOn();

        //then
        assertThat(responses).isEqualTo(getWorkingOnResponses);
        verify(listWorkingOnAdapter, times(1)).listWorkingOn();
        verify(workingOnMapper, times(1)).map(workingOn1);
        verify(workingOnMapper, times(1)).map(workingOn2);
        verifyNoMoreInteractions(listWorkingOnAdapter);
        verifyNoMoreInteractions(workingOnMapper);
    }
}
