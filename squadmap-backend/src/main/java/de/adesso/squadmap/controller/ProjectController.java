package de.adesso.squadmap.controller;

import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    private final GetProjectUseCase getProjectUseCase;
    private final ListProjectUseCase listProjectUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    public ProjectController(
            GetProjectUseCase getProjectUseCase,
            ListProjectUseCase listProjectUseCase,
            CreateProjectUseCase createProjectUseCase,
            UpdateProjectUseCase updateProjectUseCase,
            DeleteProjectUseCase deleteProjectUseCase) {
        this.getProjectUseCase = getProjectUseCase;
        this.listProjectUseCase = listProjectUseCase;
        this.createProjectUseCase = createProjectUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
        this.deleteProjectUseCase = deleteProjectUseCase;
    }

    @GetMapping("/all")
    public List<GetProjectResponse> getAllProjects(){
        return listProjectUseCase.listProjects();
    }

    @GetMapping("/{projectId}")
    public GetProjectResponse getProject(@PathVariable long projectId){
        return getProjectUseCase.getProject(projectId);
    }

    @PostMapping("/create")
    public Long createProject(@RequestBody CreateProjectCommand command){
        return createProjectUseCase.createProject(command);
    }

    @PutMapping("/update/{projectId}")
    public void updateProject(@RequestBody UpdateProjectCommand command, @PathVariable long projectId){
        updateProjectUseCase.updateProject(command, projectId);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable long projectId){
        deleteProjectUseCase.deleteProject(projectId);
    }
}
