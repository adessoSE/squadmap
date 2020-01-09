package de.adesso.squadmap.controller;

import de.adesso.squadmap.exceptions.employee.*;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    public Long createEmployee(@RequestBody @Valid CreateEmployeeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(bindingResult);
        }
        return createEmployeeUseCase.createEmployee(command);
    }

    @PutMapping("/update/{employeeId}")
    public void updateEmployee(@PathVariable long employeeId, @RequestBody @Valid UpdateEmployeeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(bindingResult);
        }
        updateEmployeeUseCase.updateEmployee(command, employeeId);
    }

    @DeleteMapping("delete/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        deleteEmployeeUseCase.deleteEmployee(employeeId);
    }

    private void throwError(BindingResult result) {
        switch (Objects.requireNonNull(result.getFieldError()).getField()) {
            case "firstName":
                throw new InvalidEmployeeFirstNameException();
            case "lastName":
                throw new InvalidEmployeeLastNameException();
            case "birthday":
                throw new InvalidEmployeeBirthdayException();
            case "email":
                throw new InvalidEmployeeEmailException();
            case "phone":
                throw new InvalidEmployeePhoneNumberException();
            default:
                throw new IllegalArgumentException("Invalid input");
        }
    }
}
