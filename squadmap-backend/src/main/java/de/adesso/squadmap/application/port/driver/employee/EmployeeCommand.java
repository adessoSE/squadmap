package de.adesso.squadmap.application.port.driver.employee;

import de.adesso.squadmap.common.SelfValidating;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class EmployeeCommand extends SelfValidating<EmployeeCommand> {

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

    public EmployeeCommand(String firstName,
                           String lastName,
                           LocalDate birthday,
                           String email,
                           String phone,
                           Boolean isExternal,
                           String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.isExternal = isExternal;
        this.image = image;
        this.validateSelf();
    }
}
