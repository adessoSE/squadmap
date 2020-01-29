package de.adesso.squadmap.application.port.driver.employee.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.adesso.squadmap.application.port.driver.employee.EmployeeCommand;
import lombok.*;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateEmployeeCommand extends EmployeeCommand {

    @Builder
    @JsonCreator
    public CreateEmployeeCommand(
            @JsonProperty String firstName,
            @JsonProperty String lastName,
            @JsonProperty LocalDate birthday,
            @JsonProperty String email,
            @JsonProperty String phone,
            @JsonProperty boolean isExternal,
            @JsonProperty String image) {
        super(firstName, lastName, birthday, email, phone, isExternal, image);
    }
}
