package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
class ListProjectAdapter implements ListProjectPort {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    public List<Project> listProjects() {
        List<Project> projects = new ArrayList<>(Collections.emptyList());
        projectRepository.findAll().forEach(projectDto -> projects.add(mapper.mapToDomainEntity(projectDto)));
        return projects;
    }
}
