package Controller;

import Service.ProjectService;
import models.Project;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("")
    public Iterable<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public Optional<Project> getProjectById(@PathVariable long projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/create")
    public Project createProject(Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/update/{projectId}")
    public Project updateProject(@PathVariable long projectId, Project project) {
        return projectService.saveProject(project);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.deleteProjectById(projectId);
    }
}
