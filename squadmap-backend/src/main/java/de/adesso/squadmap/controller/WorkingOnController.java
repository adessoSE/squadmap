package de.adesso.squadmap.controller;

import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingOn.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingOn.get.ListWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/workingOn")
public class WorkingOnController {

    private final GetWorkingOnUseCase getWorkingOnUseCase;
    private final ListWorkingOnUseCase listWorkingOnUseCase;
    private final CreateWorkingOnUseCase createWorkingOnUseCase;
    private final UpdateWorkingOnUseCase updateWorkingOnUseCase;
    private final DeleteWorkingOnUseCase deleteWorkingOnUseCase;

    public WorkingOnController(
            GetWorkingOnUseCase getWorkingOnUseCase,
            ListWorkingOnUseCase listWorkingOnUseCase,
            CreateWorkingOnUseCase createWorkingOnUseCase,
            UpdateWorkingOnUseCase updateWorkingOnUseCase,
            DeleteWorkingOnUseCase deleteWorkingOnUseCase) {
        this.getWorkingOnUseCase = getWorkingOnUseCase;
        this.listWorkingOnUseCase = listWorkingOnUseCase;
        this.createWorkingOnUseCase = createWorkingOnUseCase;
        this.updateWorkingOnUseCase = updateWorkingOnUseCase;
        this.deleteWorkingOnUseCase = deleteWorkingOnUseCase;
    }

    @GetMapping("/all")
    public List<GetWorkingOnResponse> getAllWorkingOn() {
        return listWorkingOnUseCase.listWorkingOn();
    }

    @GetMapping("/{workingOnId}")
    public GetWorkingOnResponse getWorkingOn(@PathVariable long workingOnId) {
        return getWorkingOnUseCase.getWorkingOn(workingOnId);
    }

    @PostMapping("/create")
    public Long createWorkingOn(@RequestBody CreateWorkingOnCommand command) {
        return createWorkingOnUseCase.createWorkingOn(command);
    }

    @PutMapping("/update/{workingOnId}")
    public void updateWorkingOn(@RequestBody UpdateWorkingOnCommand command, @PathVariable long workingOnId) {
        updateWorkingOnUseCase.updateWorkingOn(command, workingOnId);
    }

    @DeleteMapping("/delete/{workingOnId}")
    public void deleteWorkingOn(@PathVariable long workingOnId) {
        deleteWorkingOnUseCase.deleteWorkingOn(workingOnId);
    }
}
