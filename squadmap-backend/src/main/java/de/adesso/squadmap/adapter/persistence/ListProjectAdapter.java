package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
class ListProjectAdapter implements ListProjectPort {

    private final ProjectRepository projectRepository;
    private final ProjectPersistenceMapper mapper;

    @Override
    public List<Project> listProjects() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
