package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.utility.Mapper;
import de.adesso.squadmap.utility.ProjectToResponseMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListProjectService implements ListProjectUseCase {

    private final ProjectRepository projectRepository;
    private final Mapper<Project, GetProjectResponse> projectMapper;

    public ListProjectService(ProjectRepository projectRepository, ProjectToResponseMapper projectMapper){
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<GetProjectResponse> listProjects() {
        List<GetProjectResponse> responses = new ArrayList<>();
        projectRepository.findAll().forEach(project ->
                        responses.add(projectMapper.map(project)));
        return responses;
    }
}
