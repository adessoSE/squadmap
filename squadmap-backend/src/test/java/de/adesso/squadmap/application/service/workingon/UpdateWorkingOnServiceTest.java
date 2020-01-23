package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateWorkingOnServiceTest {

    @Autowired
    private UpdateWorkingOnService service;
    @MockBean
    private UpdateWorkingOnPort updateWorkingOnPort;
    @MockBean
    private GetEmployeePort getEmployeePort;
    @MockBean
    private GetProjectPort getProjectPort;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        long projectId = 1;
        Employee employee = Employee.withId(
                employeeId, "", "", null, "", "", true, "");
        Project project = Project.withId(
                projectId, "", "", null, null, true, null);
        UpdateWorkingOnCommand updateWorkingOnCommand = new UpdateWorkingOnCommand(
                employeeId, projectId, null, null, 0);
        WorkingOn workingOn = updateWorkingOnCommand.toWorkingOn(workingOnId, employee, project);
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectPort.getProject(projectId)).thenReturn(project);
        doNothing().when(updateWorkingOnPort).updateWorkingOn(workingOn);

        //when
        service.updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //then
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(getProjectPort, times(1)).getProject(projectId);
        verify(updateWorkingOnPort, times(1)).updateWorkingOn(workingOn);
        verifyNoMoreInteractions(getEmployeePort);
        verifyNoMoreInteractions(getProjectPort);
        verifyNoMoreInteractions(updateWorkingOnPort);
    }
}
