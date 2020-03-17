package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import org.springframework.stereotype.Component;

@Component
class WorkingOnDomainMapperImplementation implements WorkingOnDomainMapper {

    public WorkingOn mapToDomainEntity(CreateWorkingOnCommand command, Employee employee, Project project) {
        return new WorkingOn(
                null,
                command.getSince(),
                command.getUntil(),
                command.getWorkload(),
                employee,
                project);
    }

    public WorkingOn mapToDomainEntity(UpdateWorkingOnCommand command, long workingOnId, Employee employee, Project project) {
        return new WorkingOn(
                workingOnId,
                command.getSince(),
                command.getUntil(),
                command.getWorkload(),
                employee,
                project);
    }
}
