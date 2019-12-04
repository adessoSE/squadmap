package de.adesso.squadmap.port.driver.employee.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateEmployeeCommand {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Boolean isExternal;

    public UpdateEmployeeCommand() {
    }

    public UpdateEmployeeCommand(
            String firstName,
            String lastName,
            LocalDate birthday,
            String email,
            String phone,
            boolean isExternal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.isExternal = isExternal;
    }
}
