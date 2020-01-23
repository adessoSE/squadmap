package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort updateProjectPort;

    @Override
    public void updateProject(UpdateProjectCommand command, Long projectId) {
        updateProjectPort.updateProject(command.toProject(projectId));
    }
}
