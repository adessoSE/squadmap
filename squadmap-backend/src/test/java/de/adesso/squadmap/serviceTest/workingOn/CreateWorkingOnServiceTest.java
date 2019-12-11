package de.adesso.squadmap.serviceTest.workingOn;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingOn.CreateWorkingOnService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class CreateWorkingOnServiceTest {

    @Autowired
    private CreateWorkingOnService service;
    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 1;
        long workingOnId = 1;
        Employee employee = new Employee();
        Project project = new Project();
        CreateWorkingOnCommand command = new CreateWorkingOnCommand(employeeId, projectId, LocalDate.now(), LocalDate.now());
        WorkingOn workingOn = new WorkingOn(employee, project, command.getSince(), command.getUntil());
        workingOn.setWorkingOnId(workingOnId);
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        Mockito.when(workingOnRepository.save(any(WorkingOn.class))).thenReturn(workingOn);

        //when
        long found = service.createWorkingOn(command);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(workingOnRepository, times(1)).save(any(WorkingOn.class));
    }

    @Test
    void checkIfCreateWorkingOnThrowsExceptionWhenEmployeeNotFound() {
        //given
        long employeeId = 1;
        CreateWorkingOnCommand command = new CreateWorkingOnCommand(employeeId, 0, LocalDate.now(), LocalDate.now());
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () ->
                service.createWorkingOn(command));
    }

    @Test
    void checkIfCreateWorkingOnThrowsExceptionWhenProjectNotFound() {
        //given
        long employeeId = 1;
        long projectId = 1;
        CreateWorkingOnCommand command = new CreateWorkingOnCommand(employeeId, projectId, LocalDate.now(), LocalDate.now());
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(false);

        //then
        assertThrows(ProjectNotFoundException.class, () ->
                service.createWorkingOn(command));
    }

    @Test
    void checkIfCreateWorkingOnThrowsExceptionWhenTheRelationAlreadyExists() {
        //given
        long employeeId = 1;
        long projectId = 1;
        Employee employee = new Employee();
        Project project = new Project();
        CreateWorkingOnCommand command = new CreateWorkingOnCommand(employeeId, projectId, LocalDate.now(), LocalDate.now());
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(true);

        //then
        assertThrows(WorkingOnAlreadyExistsException.class, () ->
                service.createWorkingOn(command));
    }
}
