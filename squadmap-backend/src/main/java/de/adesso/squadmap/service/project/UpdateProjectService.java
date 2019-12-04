package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {


    @Override
    public void updateProject(UpdateProjectCommand command, Long projectId) {

    }
}
