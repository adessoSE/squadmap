package de.adesso.squadmap.adapter.project;

import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driven.project.DeleteProjectPort;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteProjectAdapter implements DeleteProjectPort {

    private ProjectRepository projectRepository;

    public DeleteProjectAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        projectRepository.deleteById(projectId);
    }
}
