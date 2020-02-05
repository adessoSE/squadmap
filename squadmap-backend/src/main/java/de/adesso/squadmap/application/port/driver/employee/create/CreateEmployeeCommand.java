package de.adesso.squadmap.application.port.driver.employee.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.adesso.squadmap.application.port.driver.employee.EmployeeCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateEmployeeCommand extends EmployeeCommand {

    @Builder
    @JsonCreator
    public CreateEmployeeCommand(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("birthday") LocalDate birthday,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("isExternal") Boolean isExternal,
            @JsonProperty("image") String image) {
        super(firstName, lastName, birthday, email, phone, isExternal, image);
    }
}
