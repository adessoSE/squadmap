package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;

public interface WorkingOnDomainMapper {

    WorkingOn mapToDomainEntity(CreateWorkingOnCommand command, Employee employee, Project project);

    WorkingOn mapToDomainEntity(UpdateWorkingOnCommand command, long workingOnId, Employee employee, Project project);
}
