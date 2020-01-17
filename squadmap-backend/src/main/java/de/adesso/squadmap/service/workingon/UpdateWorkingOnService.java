package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnUseCase;
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
        Employee employee = employeeRepository.findById(command.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(ProjectNotFoundException::new);
        WorkingOn workingOn = workingOnRepository.findById(workingOnId)
                .orElseThrow(WorkingOnNotFoundException::new);
        workingOn.setUntil(command.getUntil());
        workingOn.setSince(command.getSince());
        workingOn.setEmployee(employee);
        workingOn.setProject(project);
        workingOnRepository.save(workingOn);
    }
}
