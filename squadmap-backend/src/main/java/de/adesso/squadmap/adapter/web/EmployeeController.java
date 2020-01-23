package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.*;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/employee")
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
    public Long createEmployee(@RequestBody @Valid CreateEmployeeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(Objects.requireNonNull(bindingResult.getFieldError()));
        }
        return createEmployeeUseCase.createEmployee(command);
    }

    @PutMapping("/update/{employeeId}")
    public void updateEmployee(@PathVariable long employeeId, @RequestBody @Valid UpdateEmployeeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throwError(Objects.requireNonNull(bindingResult.getFieldError()));
        }
        updateEmployeeUseCase.updateEmployee(command, employeeId);
    }

    @DeleteMapping("delete/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        deleteEmployeeUseCase.deleteEmployee(employeeId);
    }

    private void throwError(FieldError error) {
        String field = error.getField();
        switch (field) {
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
            case "image":
                throw new InvalidEmployeeImageException();
        }
        throw new IllegalArgumentException("Invalid input");
    }
}
