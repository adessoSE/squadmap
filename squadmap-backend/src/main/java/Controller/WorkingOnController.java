package Controller;

import Service.WorkingOnService;
import models.WorkingOn;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/workingOn")
public class WorkingOnController {

    private WorkingOnService workingOnService;

    public WorkingOnController(WorkingOnService workingOnService) {
        this.workingOnService = workingOnService;
    }

    @GetMapping("")
    public Iterable<WorkingOn> getAllWorkingOn() {
        return workingOnService.getAllWorkingOn();
    }

    @GetMapping("/{workingOnId}")
    public Optional<WorkingOn> getWorkingOnById(@PathVariable long workingOnId) {
        return workingOnService.getWorkingOnById(workingOnId);
    }

    @PostMapping("/create")
    public WorkingOn createWorkingOn(WorkingOn workingOn) {
        return workingOnService.saveWorkingOn(workingOn);
    }

    @PutMapping("/update/{workingOnId}")
    public WorkingOn updateWorkingOn(@PathVariable long workingOnId, WorkingOn workingOn) {
        return workingOnService.saveWorkingOn(workingOn);
    }

    @DeleteMapping("/delete/{workingOnId}")
    public void deleteWorkingOn(@PathVariable long workingOnId) {
        workingOnService.deleteWorkingOnById(workingOnId);
    }

}
