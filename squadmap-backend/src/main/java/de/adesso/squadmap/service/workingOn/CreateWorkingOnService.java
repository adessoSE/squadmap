package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingOn.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkingOnService implements CreateWorkingOnUseCase {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final WorkingOnRepository workingOnRepository;

    public CreateWorkingOnService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, WorkingOnRepository workingOnRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public Long createWorkingOn(CreateWorkingOnCommand command) {
        if (!employeeRepository.existsById(command.getEmployeeId())) {
            throw new EmployeeNotFoundException();
        }
        if (!projectRepository.existsById(command.getProjectId())) {
            throw new ProjectNotFoundException();
        }
        if (workingOnRepository.existsByEmployeeAndProject(command.getEmployeeId(), command.getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        Employee employee = employeeRepository.findById(command.getEmployeeId()).orElse(null);
        Project project = projectRepository.findById(command.getProjectId()).orElse(null);
        WorkingOn workingOn = new WorkingOn(
                employee,
                project,
                command.getSince(),
                command.getUntil());
        return workingOnRepository.save(workingOn).getWorkingOnId();
    }
}
