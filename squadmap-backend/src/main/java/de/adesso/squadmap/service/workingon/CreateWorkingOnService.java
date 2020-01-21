package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingon.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnUseCase;
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
        if (workingOnRepository.existsByEmployeeAndProject(command.getEmployeeId(), command.getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        Employee employee = employeeRepository.findById(command.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(ProjectNotFoundException::new);
        WorkingOn workingOn = new WorkingOn(
                employee,
                project,
                command.getSince(),
                command.getUntil(),
                command.getWorkload());
        return workingOnRepository.save(workingOn).getWorkingOnId();
    }
}
