package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnSinceException;
import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnUntilException;
import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnWorkloadException;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/workingOn")
@RequiredArgsConstructor
class WorkingOnController {

    private final GetWorkingOnUseCase getWorkingOnUseCase;
    private final ListWorkingOnUseCase listWorkingOnUseCase;
    private final CreateWorkingOnUseCase createWorkingOnUseCase;
    private final UpdateWorkingOnUseCase updateWorkingOnUseCase;
    private final DeleteWorkingOnUseCase deleteWorkingOnUseCase;

    @GetMapping("/all")
    public List<GetWorkingOnResponse> getAllWorkingOn() {
        return listWorkingOnUseCase.listWorkingOn();
    }

    @GetMapping("/{workingOnId}")
    public GetWorkingOnResponse getWorkingOn(@PathVariable long workingOnId) {
        return getWorkingOnUseCase.getWorkingOn(workingOnId);
    }

    @PostMapping("/create")
    public Long createWorkingOn(@RequestBody @Valid CreateWorkingOnCommand command, BindingResult bindingResult) {
        checkInput(bindingResult);
        return createWorkingOnUseCase.createWorkingOn(command);
    }

    @PutMapping("/update/{workingOnId}")
    public void updateWorkingOn(@PathVariable long workingOnId, @RequestBody @Valid UpdateWorkingOnCommand command, BindingResult bindingResult) {
        checkInput(bindingResult);
        updateWorkingOnUseCase.updateWorkingOn(command, workingOnId);
    }

    @DeleteMapping("/delete/{workingOnId}")
    public void deleteWorkingOn(@PathVariable long workingOnId) {
        deleteWorkingOnUseCase.deleteWorkingOn(workingOnId);
    }

    private void checkInput(BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            switch (Objects.requireNonNull(bindingResult.getFieldError()).getField()) {
                case "since":
                    throw new InvalidWorkingOnSinceException();
                case "until":
                    throw new InvalidWorkingOnUntilException();
                case "workload":
                    throw new InvalidWorkingOnWorkloadException();
                default:
                    throw new IllegalArgumentException("Invalid input");
            }
        }
    }
}
