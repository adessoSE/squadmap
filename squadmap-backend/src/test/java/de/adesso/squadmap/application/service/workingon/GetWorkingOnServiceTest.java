package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetWorkingOnServiceTest {

    @Autowired
    private GetWorkingOnService service;
    @MockBean
    private GetWorkingOnPort getWorkingOnPort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        Employee employee = Employee.withoutId(
                "", "",  null, "", "",  true, "");
        Project project = Project.withoutId(
                "", "", null, null, true, null);
        WorkingOn workingOn = WorkingOn.withId(
                workingOnId, null, null, 0, employee, project);
        List<WorkingOn> allRelations = new ArrayList<>();
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponse.getInstance(workingOn, allRelations);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(getWorkingOnPort.getWorkingOn(workingOnId)).thenReturn(workingOn);

        //when
        GetWorkingOnResponse response = service.getWorkingOn(workingOnId);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(getWorkingOnPort, times(1)).getWorkingOn(workingOnId);
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(getWorkingOnPort);
    }
}
