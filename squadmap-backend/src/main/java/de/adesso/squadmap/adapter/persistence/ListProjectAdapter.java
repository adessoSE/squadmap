package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PersistenceAdapter
@RequiredArgsConstructor
class ListProjectAdapter implements ListProjectPort {

    private final ProjectRepository projectRepository;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;

    @Override
    public List<Project> listProjects() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .map(projectPersistenceMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
