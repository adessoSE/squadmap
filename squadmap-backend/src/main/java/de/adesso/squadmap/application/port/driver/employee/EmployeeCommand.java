package de.adesso.squadmap.application.port.driver.employee;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public abstract class EmployeeCommand {

    @NotEmpty
    @Size(min = 1, max = 50)
    private final String firstName;
    @NotEmpty
    @Size(min = 1, max = 50)
    private final String lastName;
    @NotNull
    @Past
    private final LocalDate birthday;
    @NotEmpty
    @Email
    private final String email;
    @NotNull
    @Size(max = 20)
    private final String phone;
    @NotNull
    private final Boolean isExternal;
    @NotNull
    private final String image;
}
