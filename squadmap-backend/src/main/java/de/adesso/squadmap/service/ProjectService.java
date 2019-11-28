package de.adesso.squadmap.service;

import de.adesso.squadmap.models.Project;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(long id) {
        return projectRepository.findById(id);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void deleteById(long projectId) {
        projectRepository.deleteById(projectId);
    }
}
