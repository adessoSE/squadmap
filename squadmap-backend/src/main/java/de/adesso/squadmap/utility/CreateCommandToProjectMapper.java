package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import org.springframework.stereotype.Component;

@Component
public class CreateCommandToProjectMapper implements Mapper<CreateProjectCommand, Project> {

    @Override
    public Project map(CreateProjectCommand command) {
        return new Project(
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.isExternal());
    }
}
