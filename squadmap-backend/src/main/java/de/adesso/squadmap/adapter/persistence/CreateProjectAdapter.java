package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateProjectAdapter implements CreateProjectPort {

    private final ProjectRepository projectRepository;
    private final ProjectPersistenceMapper mapper;

    @Override
    public long createProject(Project project) {
        if (projectRepository.existsByTitle(project.getTitle())) {
            throw new ProjectAlreadyExistsException();
        }
        return projectRepository.save(mapper.mapToNeo4JEntity(project)).getProjectId();
    }
}
