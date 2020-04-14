package de.adesso.squadmap.application.port.driver.project.update;

public interface UpdateProjectUseCase {

    void updateProject(UpdateProjectCommand command, Long projectId);
}
