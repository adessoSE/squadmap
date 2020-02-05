package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOnDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class CreateWorkingOnService implements CreateWorkingOnUseCase {

    private final CreateWorkingOnPort createWorkingOnPort;
    private final GetEmployeePort getEmployeePort;
    private final GetProjectPort getProjectPort;
    private final WorkingOnDomainMapper workingOnMapper;

    @Override
    @Transactional
    public Long createWorkingOn(CreateWorkingOnCommand command) {
        return createWorkingOnPort.createWorkingOn(workingOnMapper.mapToDomainEntity(
                command,
                getEmployeePort.getEmployee(command.getEmployeeId()),
                getProjectPort.getProject(command.getProjectId())));

    }
}
