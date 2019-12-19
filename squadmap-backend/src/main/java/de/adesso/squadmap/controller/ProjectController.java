package de.adesso.squadmap.controller;

import de.adesso.squadmap.exceptions.project.InvalidProjectDescriptionException;
import de.adesso.squadmap.exceptions.project.InvalidProjectSinceException;
import de.adesso.squadmap.exceptions.project.InvalidProjectTitleException;
import de.adesso.squadmap.exceptions.project.InvalidProjectUntilException;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectUseCase;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    public List<GetProjectResponse> getAllProjects() {
        return listProjectUseCase.listProjects();
    }

    @GetMapping("/{projectId}")
    public GetProjectResponse getProject(@PathVariable long projectId) {
        return getProjectUseCase.getProject(projectId);
    }

    @PostMapping("/create")
    public Long createProject(@RequestBody @Valid CreateProjectCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(bindingResult);
        }
        return createProjectUseCase.createProject(command);
    }

    @PutMapping("/update/{projectId}")
    public void updateProject(@PathVariable long projectId, @RequestBody @Valid UpdateProjectCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(bindingResult);
        }
        updateProjectUseCase.updateProject(command, projectId);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
    }

    private void throwError(BindingResult result) {
        switch (Objects.requireNonNull(result.getFieldError()).getField()) {
            case "title":
                throw new InvalidProjectTitleException();
            case "description":
                throw new InvalidProjectDescriptionException();
            case "since":
                throw new InvalidProjectSinceException();
            case "until":
                throw new InvalidProjectUntilException();
            default:
                throw new IllegalArgumentException("Invalid input");
        }
    }
}
