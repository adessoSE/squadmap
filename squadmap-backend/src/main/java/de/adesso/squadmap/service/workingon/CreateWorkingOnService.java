package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.port.driven.project.GetProjectPort;
import de.adesso.squadmap.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkingOnService implements CreateWorkingOnUseCase {

    private final CreateWorkingOnPort createWorkingOnPort;
    private final GetEmployeePort getEmployeePort;
    private final GetProjectPort getProjectPort;

    public CreateWorkingOnService(CreateWorkingOnPort createWorkingOnPort, GetEmployeePort getEmployeePort, GetProjectPort getProjectPort) {
        this.createWorkingOnPort = createWorkingOnPort;
        this.getEmployeePort = getEmployeePort;
        this.getProjectPort = getProjectPort;
    }

    @Override
    public Long createWorkingOn(CreateWorkingOnCommand command) {
        return createWorkingOnPort.createWorkingOn(
                new WorkingOn(
                        getEmployeePort.getEmployee(command.getEmployeeId()),
                        getProjectPort.getProject(command.getProjectId()),
                        command.getSince(),
                        command.getUntil(),
                        command.getWorkload()));
    }
}
