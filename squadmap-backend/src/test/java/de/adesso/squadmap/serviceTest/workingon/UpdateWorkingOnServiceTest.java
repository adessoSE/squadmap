package de.adesso.squadmap.serviceTest.workingon;

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
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        long projectId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        Project project = new Project();
        project.setProjectId(projectId);
        WorkingOn workingOn = new WorkingOn();
        workingOn.setWorkingOnId(workingOnId);
        UpdateWorkingOnCommand command = new UpdateWorkingOnCommand(employeeId, projectId, LocalDate.now(), LocalDate.now());
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(workingOnRepository.findById(workingOnId)).thenReturn(Optional.of(workingOn));
        when(workingOnRepository.save(workingOn)).thenReturn(workingOn);

        //when
        service.updateWorkingOn(command, workingOnId);

        //then
        assertThat(workingOn.getEmployee().getEmployeeId()).isEqualTo(command.getEmployeeId());
        assertThat(workingOn.getProject().getProjectId()).isEqualTo(command.getProjectId());
        assertThat(workingOn.getSince()).isEqualTo(command.getSince());
        assertThat(workingOn.getUntil()).isEqualTo(command.getUntil());
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(workingOnRepository, times(1)).findById(workingOnId);
        verify(workingOnRepository, times(1)).save(workingOn);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsExceptionWhenWorkingOnNotFound() {
        //given
        long workingOnId = 1;
        UpdateWorkingOnCommand command = new UpdateWorkingOnCommand();
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //then
        assertThrows(WorkingOnNotFoundException.class, () ->
                service.updateWorkingOn(command, workingOnId));
    }

    @Test
    void checkIfUpdateWorkingOnThrowsExceptionWhenEmployeeNotFound() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        UpdateWorkingOnCommand command = new UpdateWorkingOnCommand(employeeId, 0, null, null);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () ->
                service.updateWorkingOn(command, workingOnId));
    }

    @Test
    void checkIfUpdateWorkingOnThrowsExceptionWhenProjectNotFound() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        long projectId = 1;
        UpdateWorkingOnCommand command = new UpdateWorkingOnCommand(employeeId, projectId, null, null);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(false);

        //then
        assertThrows(ProjectNotFoundException.class, () ->
                service.updateWorkingOn(command, workingOnId));
    }
}
