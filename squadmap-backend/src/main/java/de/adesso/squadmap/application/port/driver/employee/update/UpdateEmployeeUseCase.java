package de.adesso.squadmap.application.port.driver.employee.update;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UpdateEmployeeUseCase {

    void updateEmployee(@Valid UpdateEmployeeCommand updateEmployeeCommand, Long employeeId);
}
