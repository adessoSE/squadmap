package de.adesso.squadmap.serviceTest.workingon;

import de.adesso.squadmap.adapter.employee.GetEmployeeAdapter;
import de.adesso.squadmap.adapter.project.GetProjectAdapter;
import de.adesso.squadmap.adapter.workingon.UpdateWorkingOnAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingon.UpdateWorkingOnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateWorkingOnServiceTest {

    @Autowired
    private UpdateWorkingOnService service;
    @MockBean
    private UpdateWorkingOnAdapter updateWorkingOnAdapter;
    @MockBean
    private GetEmployeeAdapter getEmployeeAdapter;
    @MockBean
    private GetProjectAdapter getProjectAdapter;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        long projectId = 1;
        Employee employee = new Employee();
        Project project = new Project();
        UpdateWorkingOnCommand updateWorkingOnCommand = new UpdateWorkingOnCommand();
        updateWorkingOnCommand.setEmployeeId(employeeId);
        updateWorkingOnCommand.setProjectId(projectId);
        when(getEmployeeAdapter.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectAdapter.getProject(projectId)).thenReturn(project);
        doNothing().when(updateWorkingOnAdapter).updateWorkingOn(any(WorkingOn.class));

        //when
        service.updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //then
        verify(getEmployeeAdapter, times(1)).getEmployee(employeeId);
        verify(getProjectAdapter, times(1)).getProject(projectId);
        verify(updateWorkingOnAdapter, times(1)).updateWorkingOn(any(WorkingOn.class));
        verifyNoMoreInteractions(getEmployeeAdapter);
        verifyNoMoreInteractions(getProjectAdapter);
        verifyNoMoreInteractions(updateWorkingOnAdapter);
    }
}
