package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingOn.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkingOnService implements UpdateWorkingOnUseCase {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final WorkingOnRepository workingOnRepository;

    public UpdateWorkingOnService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, WorkingOnRepository workingOnRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {
        if (!workingOnRepository.existsById(workingOnId)) {
            throw new WorkingOnNotFoundException();
        }
        if (!employeeRepository.existsById(command.getEmployeeId())) {
            throw new EmployeeNotFoundException();
        }
        if (!projectRepository.existsById(command.getProjectId())) {
            throw new ProjectNotFoundException();
        }
        Employee employee = employeeRepository.findById(command.getEmployeeId()).orElse(null);
        Project project = projectRepository.findById(command.getProjectId()).orElse(null);
        WorkingOn workingOn = workingOnRepository.findById(workingOnId).orElse(null);
        workingOn.setUntil(command.getUntil());
        workingOn.setSince(command.getSince());
        workingOn.setEmployee(employee);
        workingOn.setProject(project);
        workingOnRepository.save(workingOn);
    }
}
