package de.adesso.squadmap.adapter.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateProjectAdapter implements UpdateProjectPort {

    private ProjectRepository projectRepository;

    public UpdateProjectAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void updateProject(Project project) {
        Project existingProject = projectRepository.findById(project.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        if (projectRepository.existsByTitle(project.getTitle()) && !existingProject.getTitle().equals(project.getTitle())) {
            throw new ProjectAlreadyExistsException();
        }
        projectRepository.save(project);
    }
}
