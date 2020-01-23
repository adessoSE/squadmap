package de.adesso.squadmap.application.port.driver.employee.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.Employee;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "UpdateEmployeeCommandBuilder")
@JsonDeserialize(builder = UpdateEmployeeCommand.UpdateEmployeeCommandBuilder.class)
public class UpdateEmployeeCommand{

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
    @Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private final String email;
    @NotEmpty
    @Pattern(regexp = "(\\(?([\\d \\-)–+/(]+){6,}\\)?([ .\\-–/]?)([\\d]+))")
    private final String phone;
    @JsonProperty
    private final boolean isExternal;
    @NotNull
    @URL(protocol = "file")
    private final String image;

    public Employee toEmployee(long employeeId) {
        return Employee.withId(
                employeeId,
                this.firstName,
                this.lastName,
                this.birthday,
                this.email,
                this.phone,
                this.isExternal,
                this.image);
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class UpdateEmployeeCommandBuilder { }
}
