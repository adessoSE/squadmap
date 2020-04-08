package de.adesso.squadmap.application.port.driver.employee;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public abstract class EmployeeCommand {

    @NotBlank(message = "should not be empty")
    @Size(max = 50, message = "should be maximal {max} characters long")
    private final String firstName;
    @NotBlank(message = "should not be empty")
    @Size(max = 50, message = "should be maximal {max} characters long")
    private final String lastName;
    @NotNull(message = "should not be null")
    @Past(message = "has to be in past")
    private final LocalDate birthday;
    @NotBlank(message = "should not be empty")
    @Email(message = "has to be a valid email")
    private final String email;
    @NotNull(message = "should not be null")
    @Size(max = 20, message = "should contain maximal {max} digits")
    private final String phone;
    @NotNull(message = "should not be null")
    private final Boolean isExternal;
    @NotNull(message = "should not be null")
    private final String image;
}
