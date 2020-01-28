package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
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

    @JsonPOJOBuilder(withPrefix = "")
    static class GetEmployeeResponseWithoutProjectBuilder {
    }
}
