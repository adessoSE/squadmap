package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class UpdateProjectAdapter implements UpdateProjectPort {

    private final ProjectRepository projectRepository;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;

    @Override
    public void updateProject(Project project) {
        ProjectNeo4JEntity existingProject = projectRepository.findById(project.getProjectId(), 0)
                .orElseThrow(() -> new ProjectNotFoundException(project.getProjectId()));
        if (projectRepository.existsByTitle(project.getTitle()) && !existingProject.getTitle().equals(project.getTitle())) {
            throw new ProjectAlreadyExistsException(project.getTitle());
        }
        projectRepository.save(projectPersistenceMapper.mapToNeo4JEntity(project), 0);
    }
}
