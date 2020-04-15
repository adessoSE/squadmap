package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.employee.CreateEmployeeRequest;
import de.adesso.squadmap.adapter.web.webentities.employee.UpdateEmployeeRequest;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/employee")
@RequiredArgsConstructor
class EmployeeController {

    private final GetEmployeeUseCase getEmployeeUseCase;
    private final ListEmployeeUseCase listEmployeeUseCase;
    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;

    @GetMapping("/all")
    public List<GetEmployeeResponse> getAllEmployees() {
        return listEmployeeUseCase.listEmployees();
    }

    @GetMapping("/{employeeId}")
    public GetEmployeeResponse getEmployee(@PathVariable long employeeId) {
        return getEmployeeUseCase.getEmployee(employeeId);
    }

    @PostMapping("/create")
    public Long createEmployee(@RequestBody CreateEmployeeRequest command) {
        return createEmployeeUseCase.createEmployee(command.asCommand());
    }

    @PutMapping("/update/{employeeId}")
    public void updateEmployee(@PathVariable long employeeId, @RequestBody UpdateEmployeeRequest command) {
        updateEmployeeUseCase.updateEmployee(command.asCommand(), employeeId);
    }

    @DeleteMapping("delete/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        deleteEmployeeUseCase.deleteEmployee(employeeId);
    }
}
