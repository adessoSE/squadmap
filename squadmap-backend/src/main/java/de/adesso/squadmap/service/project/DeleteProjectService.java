package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

    private ProjectRepository projectRepository;

    public DeleteProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
