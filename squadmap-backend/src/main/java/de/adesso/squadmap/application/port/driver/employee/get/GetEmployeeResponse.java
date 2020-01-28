package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(builderClassName = "GetEmployeeResponseBuilder")
@RequiredArgsConstructor
@JsonDeserialize(builder = GetEmployeeResponse.GetEmployeeResponseBuilder.class)
public class GetEmployeeResponse {

    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final Boolean isExternal;
    private final String image;
    private final List<GetWorkingOnResponseWithoutEmployee> projects;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetEmployeeResponseBuilder { }
}
