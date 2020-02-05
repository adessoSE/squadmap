package de.adesso.squadmap.application.domain;

import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import org.springframework.stereotype.Component;

@Component
public class WorkingOnDomainMapper {

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
