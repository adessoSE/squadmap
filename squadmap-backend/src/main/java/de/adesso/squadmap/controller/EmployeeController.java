package de.adesso.squadmap.controller;

import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    private final GetEmployeeUseCase getEmployeeUseCase;
    private final ListEmployeeUseCase listEmployeeUseCase;
    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;

    public EmployeeController(
            GetEmployeeUseCase getEmployeeUseCase,
            ListEmployeeUseCase listEmployeeUseCase,
            CreateEmployeeUseCase createEmployeeUseCase,
            UpdateEmployeeUseCase updateEmployeeUseCase,
            DeleteEmployeeUseCase deleteEmployeeUseCase) {
        this.getEmployeeUseCase = getEmployeeUseCase;
        this.listEmployeeUseCase = listEmployeeUseCase;
        this.createEmployeeUseCase = createEmployeeUseCase;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
        this.deleteEmployeeUseCase = deleteEmployeeUseCase;
    }

    @GetMapping("/all")
    public List<GetEmployeeResponse> getAllEmployees() {
        return listEmployeeUseCase.listEmployees();
    }

    @GetMapping("/{employeeId}")
    public GetEmployeeResponse getEmployee(@PathVariable long employeeId) {
        return getEmployeeUseCase.getEmployee(employeeId);
    }

    @PostMapping("/create")
    public void createEmployee(@PathVariable CreateEmployeeCommand command) {
        createEmployeeUseCase.createEmployee(command);
    }

    @PutMapping("/update/{employeeId}")
    public void updateEmployee(@RequestBody UpdateEmployeeCommand command, @PathVariable long employeeId) {
        updateEmployeeUseCase.updateEmployee(command, employeeId);
    }

    @DeleteMapping("delete/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        deleteEmployeeUseCase.deleteEmployee(employeeId);
    }
}
