package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.workingon.CreateWorkingOnRequest;
import de.adesso.squadmap.adapter.web.webentities.workingon.GetWorkingOnResponse;
import de.adesso.squadmap.adapter.web.webentities.workingon.UpdateWorkingOnRequest;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final ResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;

    @GetMapping("/all")
    public List<GetWorkingOnResponse> getAllWorkingOn() {
        List<WorkingOn> workingOnList = listWorkingOnUseCase.listWorkingOn();
        return workingOnList.stream()
                .map(workingOn -> workingOnResponseMapper.mapToResponseEntity(
                        workingOn, workingOnList))
                .collect(Collectors.toList());
    }

    @GetMapping("/{workingOnId}")
    public GetWorkingOnResponse getWorkingOn(@PathVariable long workingOnId) {
        return workingOnResponseMapper.mapToResponseEntity(
                getWorkingOnUseCase.getWorkingOn(workingOnId),
                listWorkingOnUseCase.listWorkingOn());
    }

    @PostMapping("/create")
    public Long createWorkingOn(@RequestBody CreateWorkingOnRequest command) {
        return createWorkingOnUseCase.createWorkingOn(command.getCommand());
    }

    @PutMapping("/update/{workingOnId}")
    public void updateWorkingOn(@PathVariable long workingOnId, @RequestBody UpdateWorkingOnRequest command) {
        updateWorkingOnUseCase.updateWorkingOn(command.getCommand(), workingOnId);
    }

    @DeleteMapping("/delete/{workingOnId}")
    public void deleteWorkingOn(@PathVariable long workingOnId) {
        deleteWorkingOnUseCase.deleteWorkingOn(workingOnId);
    }
}
