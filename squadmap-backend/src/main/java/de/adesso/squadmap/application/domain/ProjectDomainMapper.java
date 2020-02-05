package de.adesso.squadmap.application.domain;

import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import org.springframework.stereotype.Component;

@Component
public class ProjectDomainMapper {

    public Project mapToDomainEntity(CreateProjectCommand command) {
        return new Project(
                null,
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.getIsExternal(),
                command.getSites());
    }

    public Project mapToDomainEntity(UpdateProjectCommand command, long projectId) {
        return new Project(
                projectId,
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.getIsExternal(),
                command.getSites());
    }
}
