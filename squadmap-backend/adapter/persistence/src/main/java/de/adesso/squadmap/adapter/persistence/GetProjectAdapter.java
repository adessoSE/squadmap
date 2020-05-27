package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.exceptions.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GetProjectAdapter implements GetProjectPort {

    private final ProjectRepository projectRepository;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;

    @Override
    public Project getProject(Long projectId) {
        return projectPersistenceMapper.mapToDomainEntity(projectRepository.findById(projectId, 0)
                .orElseThrow(() -> new ProjectNotFoundException(projectId)));
    }
}
