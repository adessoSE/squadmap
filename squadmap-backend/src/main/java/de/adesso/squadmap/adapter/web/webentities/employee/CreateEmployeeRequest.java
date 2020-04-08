package de.adesso.squadmap.adapter.web.webentities.employee;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import lombok.Data;

import javax.validation.Valid;

@Data
public class CreateEmployeeRequest {

    @Valid
    @JsonUnwrapped
    private CreateEmployeeCommand command;
}
