package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driven.project.DeleteProjectPort;
import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

    private final DeleteProjectPort deleteProjectPort;

    public DeleteProjectService(DeleteProjectPort deleteProjectPort) {
        this.deleteProjectPort = deleteProjectPort;
    }

    @Override
    public void deleteProject(Long projectId) {
        deleteProjectPort.deleteProject(projectId);
    }
}
