package de.adesso.squadmap.serviceTest.workingon;

import de.adesso.squadmap.adapter.employee.GetEmployeeAdapter;
import de.adesso.squadmap.adapter.project.GetProjectAdapter;
import de.adesso.squadmap.adapter.workingon.CreateWorkingOnAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingon.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingon.CreateWorkingOnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateWorkingOnServiceTest {

    @Autowired
    private CreateWorkingOnService service;
    @MockBean
    private CreateWorkingOnAdapter createWorkingOnAdapter;
    @MockBean
    private GetEmployeeAdapter getEmployeeAdapter;
    @MockBean
    private GetProjectAdapter getProjectAdapter;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 2;
        long workingOnId = 3;
        CreateWorkingOnCommand createWorkingOnCommand = new CreateWorkingOnCommand();
        createWorkingOnCommand.setEmployeeId(employeeId);
        createWorkingOnCommand.setProjectId(projectId);
        Employee employee = new Employee();
        Project project = new Project();
        when(getEmployeeAdapter.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectAdapter.getProject(projectId)).thenReturn(project);
        when(createWorkingOnAdapter.createWorkingOn(any(WorkingOn.class))).thenReturn(workingOnId);

        //when
        long found = service.createWorkingOn(createWorkingOnCommand);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(getEmployeeAdapter, times(1)).getEmployee(employeeId);
        verify(getProjectAdapter, times(1)).getProject(projectId);
        verify(createWorkingOnAdapter, times(1)).createWorkingOn(any(WorkingOn.class));
        verifyNoMoreInteractions(getEmployeeAdapter);
        verifyNoMoreInteractions(getProjectAdapter);
        verifyNoMoreInteractions(createWorkingOnAdapter);
    }
}
