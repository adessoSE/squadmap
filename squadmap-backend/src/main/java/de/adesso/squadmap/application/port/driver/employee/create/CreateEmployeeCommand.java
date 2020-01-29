package de.adesso.squadmap.application.port.driver.employee.create;

import de.adesso.squadmap.application.port.driver.employee.EmployeeCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateEmployeeCommand extends EmployeeCommand {

    @Builder
    public CreateEmployeeCommand(String firstName, String lastName, LocalDate birthday, String email, String phone, boolean isExternal, String image) {
        super(firstName, lastName, birthday, email, phone, isExternal, image);
    }
}
