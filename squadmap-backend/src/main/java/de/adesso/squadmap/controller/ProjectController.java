package de.adesso.squadmap.controller;

import de.adesso.squadmap.models.Project;
import de.adesso.squadmap.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("/{projectId}")
    public Optional<Project> getProjectById(@PathVariable long projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping("/create")
    public Project createProject(Project project) {
        return projectService.save(project);
    }

    @PutMapping("/update/{projectId}")
    public Project updateProject(@PathVariable long projectId, Project project) {
        return projectService.save(project);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.deleteById(projectId);
    }
}
