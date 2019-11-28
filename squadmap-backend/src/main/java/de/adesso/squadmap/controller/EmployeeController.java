package de.adesso.squadmap.controller;

import de.adesso.squadmap.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import de.adesso.squadmap.service.EmployeeService;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public Iterable<Employee> getAllEmployees() {
        System.out.println("hallo");
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
