package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    public DeleteProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public void deleteProject(Long projectId){
        if (!projectRepository.existsById(projectId)){
            throw new ProjectNotFoundException();
        }
        projectRepository.deleteById(projectId);
    }
}
