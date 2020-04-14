package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.employee.CreateEmployeeRequest;
import de.adesso.squadmap.adapter.web.webentities.employee.GetEmployeeResponse;
import de.adesso.squadmap.adapter.web.webentities.employee.UpdateEmployeeRequest;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final ListWorkingOnUseCase listWorkingOnUseCase;
    private final ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;

    @GetMapping("/all")
    public List<GetEmployeeResponse> getAllEmployees() {
        return listEmployeeUseCase.listEmployees().stream()
                .map(employee -> employeeResponseMapper.mapToResponseEntity(
                        employee, listWorkingOnUseCase.listWorkingOn()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public GetEmployeeResponse getEmployee(@PathVariable long employeeId) {
        return employeeResponseMapper.mapToResponseEntity(
                getEmployeeUseCase.getEmployee(employeeId),
                listWorkingOnUseCase.listWorkingOn());
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
