package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort updateProjectPort;
    private final Mapper<UpdateProjectCommand, Project> mapper;

    public UpdateProjectService(UpdateProjectPort updateProjectPort, Mapper<UpdateProjectCommand, Project> mapper) {
        this.updateProjectPort = updateProjectPort;
        this.mapper = mapper;
    }

    @Override
    public void updateProject(UpdateProjectCommand command, Long projectId) {
        Project project = mapper.map(command);
        project.setProjectId(projectId);
        updateProjectPort.updateProject(project);
    }
}
