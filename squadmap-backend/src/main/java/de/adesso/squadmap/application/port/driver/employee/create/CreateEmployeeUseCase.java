package de.adesso.squadmap.application.port.driver.employee.create;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CreateEmployeeUseCase {

    Long createEmployee(@Valid CreateEmployeeCommand createEmployeeCommand);
}
