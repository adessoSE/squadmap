package de.adesso.squadmap.controller;

import de.adesso.squadmap.models.WorkingOn;
import de.adesso.squadmap.service.WorkingOnService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/workingOn")
public class WorkingOnController {

    private WorkingOnService workingOnService;

    public WorkingOnController(WorkingOnService workingOnService) {
        this.workingOnService = workingOnService;
    }

    @GetMapping("/all")
    public Iterable<WorkingOn> getAllWorkingOn() {
        return workingOnService.findAllWorkingOn();
    }

    @GetMapping("/{workingOnId}")
    public Optional<WorkingOn> getWorkingOnById(@PathVariable long workingOnId) {
        return workingOnService.findWorkingOnById(workingOnId);
    }

    @PostMapping("/create")
    public WorkingOn createWorkingOn(@RequestBody WorkingOn workingOn) {
        return workingOnService.saveWorkingOn(workingOn);
    }

    @PutMapping("/update")
    public WorkingOn updateWorkingOn(@RequestBody WorkingOn workingOn) {
        return workingOnService.saveWorkingOn(workingOn);
    }

    @DeleteMapping("/delete")
    public void deleteWorkingOn(@RequestBody WorkingOn workingOn) {
        workingOnService.deleteWorkingOn(workingOn);
    }

    @DeleteMapping("/delete/{workingOnId}")
    public void deleteWorkingOn(@PathVariable long workingOnId) {
        workingOnService.deleteWorkingOnById(workingOnId);
    }
}
