package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.port.driven.project.GetProjectPort;
import de.adesso.squadmap.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkingOnService implements UpdateWorkingOnUseCase {

    private final UpdateWorkingOnPort updateWorkingOnPort;
    private final GetEmployeePort getEmployeePort;
    private final GetProjectPort getProjectPort;

    public UpdateWorkingOnService(UpdateWorkingOnPort updateWorkingOnPort, GetEmployeePort getEmployeePort, GetProjectPort getProjectPort) {
        this.updateWorkingOnPort = updateWorkingOnPort;
        this.getEmployeePort = getEmployeePort;
        this.getProjectPort = getProjectPort;
    }

    @Override
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {
        WorkingOn workingOn = new WorkingOn(
                getEmployeePort.getEmployee(command.getEmployeeId()),
                getProjectPort.getProject(command.getProjectId()),
                command.getSince(),
                command.getUntil(),
                command.getWorkload());
        workingOn.setWorkingOnId(workingOnId);
        updateWorkingOnPort.updateWorkingOn(workingOn);
    }
}
