package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "GetEmployeeResponseWithoutProjectBuilder")
@JsonDeserialize(builder = GetEmployeeResponseWithoutProject.GetEmployeeResponseWithoutProjectBuilder.class)
class GetEmployeeResponseWithoutProject {

    Long employeeId;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    String phone;
    Boolean isExternal;
    String image;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetEmployeeResponseWithoutProjectBuilder {
    }
}
