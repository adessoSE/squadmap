package de.adesso.squadmap.serviceTest.workingOn;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingOn.UpdateWorkingOnService;
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
        Mockito.when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(workingOnRepository.findById(workingOnId)).thenReturn(Optional.of(workingOn));
        Mockito.when(workingOnRepository.save(workingOn)).thenReturn(workingOn);

        //when
        service.updateWorkingOn(command, workingOnId);

        //then
        assertThat(workingOn.getEmployee().getEmployeeId()).isEqualTo(command.getEmployeeId());
        assertThat(workingOn.getProject().getProjectId()).isEqualTo(command.getProjectId());
        assertThat(workingOn.getSince()).isEqualTo(command.getSince());
        assertThat(workingOn.getUntil()).isEqualTo(command.getUntil());
    }

    @Test
    void checkIfUpdateWorkingONThrowsExceptionWhenNotFound() {
        //given
        long workingOnId = 1;
        UpdateWorkingOnCommand command = new UpdateWorkingOnCommand();
        Mockito.when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //then
        assertThrows(WorkingOnNotFoundException.class, () ->
                service.updateWorkingOn(command, workingOnId));
    }
}
