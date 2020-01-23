package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateWorkingOnServiceTest {

    @Autowired
    private CreateWorkingOnService service;
    @MockBean
    private CreateWorkingOnPort createWorkingOnPort;
    @MockBean
    private GetEmployeePort getEmployeePort;
    @MockBean
    private GetProjectPort getProjectPort;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 2;
        long workingOnId = 3;
        CreateWorkingOnCommand createWorkingOnCommand = new CreateWorkingOnCommand(
                employeeId, projectId, null, null, 0);
        Employee employee = Employee.withId(
                employeeId, "", "", null, "", "", true, "");
        Project project = Project.withId(
                projectId, "", "", null, null, true, null);
        WorkingOn workingOn = createWorkingOnCommand.toWorkingOn(employee, project);
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectPort.getProject(projectId)).thenReturn(project);
        when(createWorkingOnPort.createWorkingOn(workingOn)).thenReturn(workingOnId);

        //when
        long found = service.createWorkingOn(createWorkingOnCommand);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(getProjectPort, times(1)).getProject(projectId);
        verify(createWorkingOnPort, times(1)).createWorkingOn(any(WorkingOn.class));
        verifyNoMoreInteractions(getEmployeePort);
        verifyNoMoreInteractions(getProjectPort);
        verifyNoMoreInteractions(createWorkingOnPort);
    }
}
