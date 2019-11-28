package de.adesso.squadmap.controller;

import de.adesso.squadmap.models.Employee;
import de.adesso.squadmap.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> getEmployeeById(@PathVariable long employeeId) {
        return employeeService.findEmployeeById(employeeId);
    }

    @PostMapping("/create")
    public void createEmployee(Employee employee) {
        employeeService.createEmployee(employee);
    }

    @PutMapping("/update/")
    public void updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/delete/")
    public void deleteEmployee(Employee employee) {
        employeeService.deleteEmployee(employee);
    }
}
