package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.utility.ProjectMapper;
import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public GetProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper){
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public GetProjectResponse getProject(Long projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new ProjectNotFoundException();
        }
        Project project = projectRepository.findById(projectId).orElse(null);
        return projectMapper.mapProjectToGetProjectResponse(project);
    }
}
