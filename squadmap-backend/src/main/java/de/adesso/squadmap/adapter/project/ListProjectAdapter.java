package de.adesso.squadmap.adapter.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driven.project.ListProjectPort;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ListProjectAdapter implements ListProjectPort {

    private ProjectRepository projectRepository;

    public ListProjectAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> listProjects() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
