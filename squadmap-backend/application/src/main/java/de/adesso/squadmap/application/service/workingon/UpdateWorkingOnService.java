package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnUseCase;
import de.adesso.squadmap.domain.mapper.WorkingOnDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class UpdateWorkingOnService implements UpdateWorkingOnUseCase {

    private final UpdateWorkingOnPort updateWorkingOnPort;
    private final GetEmployeePort getEmployeePort;
    private final GetProjectPort getProjectPort;
    private final WorkingOnDomainMapper workingOnDomainMapper;

    @Override
    @Transactional
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {
        updateWorkingOnPort.updateWorkingOn(workingOnDomainMapper.mapToDomainEntity(
                command,
                workingOnId,
                getEmployeePort.getEmployee(command.getEmployeeId()),
                getProjectPort.getProject(command.getProjectId())));
    }
}
