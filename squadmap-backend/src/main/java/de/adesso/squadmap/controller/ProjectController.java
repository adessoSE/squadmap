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
        return projectService.findAllProjects();
    }

    @GetMapping("/{projectId}")
    public Optional<Project> getProjectById(@PathVariable long projectId) {
        return projectService.findProjectById(projectId);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/update")
    public Project updateProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @DeleteMapping("/delete")
    public void deleteProject(@RequestBody Project project) {
        projectService.deleteProject(project);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.deleteProjectById(projectId);
    }
}
