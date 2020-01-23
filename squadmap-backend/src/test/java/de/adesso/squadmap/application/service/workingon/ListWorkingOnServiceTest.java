package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
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
    private ListWorkingOnPort listWorkingOnPort;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        Employee employee = Employee.withId(
                1L, "", "",  null, "", "",  true, "");
        Project project = Project.withId(
                1L,"", "", null, null, true, null);
        WorkingOn workingOn1 = WorkingOn.withId(
                1L, null, null, 0, employee, project);
        WorkingOn workingOn2 = WorkingOn.withId(
                2L, null, null, 0, employee, project);
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        GetWorkingOnResponse response1 = GetWorkingOnResponse.getInstance(workingOn1, workingOns);
        GetWorkingOnResponse response2 = GetWorkingOnResponse.getInstance(workingOn2, workingOns);
        List<GetWorkingOnResponse> getWorkingOnResponses = Arrays.asList(response1, response2);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);

        //when
        List<GetWorkingOnResponse> responses = service.listWorkingOn();

        //then
        assertThat(responses).isEqualTo(getWorkingOnResponses);
        verify(listWorkingOnPort, times(2)).listWorkingOn();
        verifyNoMoreInteractions(listWorkingOnPort);
    }
}
