package de.adesso.squadmap.adapter.web.webentities.employee;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import lombok.Data;

import javax.validation.Valid;

@Data
public class UpdateEmployeeRequest {

    @Valid
    @JsonUnwrapped
    private UpdateEmployeeCommand command;
}
