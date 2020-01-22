package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommandToProjectMapper implements Mapper<UpdateProjectCommand, Project> {

    @Override
    public Project map(UpdateProjectCommand command) {
        return new Project(
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.isExternal(),
                command.getSites());
    }
}
