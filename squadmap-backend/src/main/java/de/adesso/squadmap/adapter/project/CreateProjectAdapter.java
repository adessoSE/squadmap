package de.adesso.squadmap.adapter.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectAdapter implements CreateProjectPort {

    private ProjectRepository projectRepository;

    public CreateProjectAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public long createProject(Project project) {
        if (projectRepository.existsByTitle(project.getTitle())) {
            throw new ProjectAlreadyExistsException();
        }
        return projectRepository.save(project).getProjectId();
    }
}
