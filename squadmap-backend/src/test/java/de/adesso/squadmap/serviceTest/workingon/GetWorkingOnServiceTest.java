package de.adesso.squadmap.serviceTest.workingon;

import de.adesso.squadmap.adapter.workingon.GetWorkingOnAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.service.workingon.GetWorkingOnService;
import de.adesso.squadmap.utility.WorkingOnToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetWorkingOnServiceTest {

    @Autowired
    private GetWorkingOnService service;
    @MockBean
    private GetWorkingOnAdapter getWorkingOnAdapter;
    @MockBean
    private WorkingOnToResponseMapper workingOnMapper;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = new WorkingOn();
        GetWorkingOnResponse getWorkingOnResponse = new GetWorkingOnResponse();
        when(getWorkingOnAdapter.getWorkingOn(workingOnId)).thenReturn(workingOn);
        when(workingOnMapper.map(workingOn)).thenReturn(getWorkingOnResponse);

        //when
        GetWorkingOnResponse response = service.getWorkingOn(workingOnId);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(getWorkingOnAdapter, times(1)).getWorkingOn(workingOnId);
        verify(workingOnMapper, times(1)).map(workingOn);
        verifyNoMoreInteractions(getWorkingOnAdapter);
        verifyNoMoreInteractions(workingOnMapper);
    }
}
