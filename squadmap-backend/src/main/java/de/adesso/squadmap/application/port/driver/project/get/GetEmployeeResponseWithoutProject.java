package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderClassName = "GetEmployeeResponseWithoutProjectBuilder")
@JsonDeserialize(builder = GetEmployeeResponseWithoutProject.GetEmployeeResponseWithoutProjectBuilder.class)
class GetEmployeeResponseWithoutProject {

    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final Boolean isExternal;
    private final String image;

    static GetEmployeeResponseWithoutProject getInstance(Employee employee) {
        return new GetEmployeeResponseWithoutProject(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                employee.getImage());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetEmployeeResponseWithoutProjectBuilder {
    }
}
