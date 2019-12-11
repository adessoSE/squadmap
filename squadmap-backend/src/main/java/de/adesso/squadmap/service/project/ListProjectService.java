package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.utility.ProjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListProjectService implements ListProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ListProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper){
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<GetProjectResponse> listProjects() {
        List<GetProjectResponse> responses = new ArrayList<>();
        projectRepository.findAll().forEach(project ->
                        responses.add(projectMapper.mapProjectToGetProjectResponse(project)));
        return responses;
    }
}
