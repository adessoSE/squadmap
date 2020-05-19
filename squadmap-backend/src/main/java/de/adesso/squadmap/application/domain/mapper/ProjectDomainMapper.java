package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;

public interface ProjectDomainMapper {

    Project mapToDomainEntity(CreateProjectCommand command);

    Project mapToDomainEntity(UpdateProjectCommand command, long projectId);
}
