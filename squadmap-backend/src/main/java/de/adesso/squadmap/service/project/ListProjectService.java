package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListProjectService implements ListProjectUseCase {

    private ProjectRepository projectRepository;

    public ListProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public List<GetProjectResponse> listProjects() {
        List<GetProjectResponse> responses = new ArrayList<>();
        projectRepository.findAll().forEach(project ->
                responses.add(new GetProjectResponse(
                        project.getProjectId(),
                        project.getTitle(),
                        project.getDescription(),
                        project.getSince(),
                        project.getUntil(),
                        project.getIsExternal())));
        return responses;
    }
}
