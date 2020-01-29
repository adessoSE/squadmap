package de.adesso.squadmap.application.port.driver.employee.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.adesso.squadmap.application.port.driver.employee.EmployeeCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateEmployeeCommand extends EmployeeCommand {

    @Builder
    @JsonCreator
    public UpdateEmployeeCommand(
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
