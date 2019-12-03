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

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> findProjectById(long id) {
        return projectRepository.findById(id);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Project project){ projectRepository.delete(project); }

    public void deleteProjectById(long projectId) {
        projectRepository.deleteById(projectId);
    }
}
