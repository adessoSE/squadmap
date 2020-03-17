package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@PersistenceAdapter
@RequiredArgsConstructor
class GetProjectAdapter implements GetProjectPort {

    private final ProjectRepository projectRepository;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> mapper;

    @Override
    public Project getProject(Long projectId) {
        return mapper.mapToDomainEntity(projectRepository.findById(projectId, 0)
                .orElseThrow(ProjectNotFoundException::new));
    }
}
