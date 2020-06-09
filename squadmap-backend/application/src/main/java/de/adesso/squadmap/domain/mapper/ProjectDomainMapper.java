package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.domain.Project;

public interface ProjectDomainMapper {

    Project mapToDomainEntity(CreateProjectCommand command);

    Project mapToDomainEntity(UpdateProjectCommand command, long projectId);
}
