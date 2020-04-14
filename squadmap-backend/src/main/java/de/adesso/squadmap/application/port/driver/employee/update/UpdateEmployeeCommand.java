package de.adesso.squadmap.application.port.driver.employee.update;

import de.adesso.squadmap.application.port.driver.employee.EmployeeCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateEmployeeCommand extends EmployeeCommand {

    @Builder
    UpdateEmployeeCommand(
            String firstName,
            String lastName,
            LocalDate birthday,
            String email,
            String phone,
            Boolean isExternal,
            String image) {
        super(firstName, lastName, birthday, email, phone, isExternal, image);
    }
}
