package de.adesso.squadmap.adapter.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driven.project.GetProjectPort;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class GetProjectAdapter implements GetProjectPort {

    private ProjectRepository projectRepository;

    public GetProjectAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
    }
}
