package de.adesso.squadmap.port.driver.project.update;

public interface UpdateProjectUseCase {

    void updateProject(UpdateProjectCommand command, Long projectId);
}
