package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdateProjectAdapter implements UpdateProjectPort {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    public void updateProject(Project project) {
        ProjectNeo4JEntity existingProject = projectRepository.findById(project.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        if (projectRepository.existsByTitle(project.getTitle()) && !existingProject.getTitle().equals(project.getTitle())) {
            throw new ProjectAlreadyExistsException();
        }
        projectRepository.save(mapper.mapToNeo4JEntity(project));
    }
}
