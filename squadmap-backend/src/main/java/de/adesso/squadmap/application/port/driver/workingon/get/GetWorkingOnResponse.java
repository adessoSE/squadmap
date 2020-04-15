package de.adesso.squadmap.application.port.driver.workingon.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "GetWorkingOnResponseBuilder")
@JsonDeserialize(builder = GetWorkingOnResponse.GetWorkingOnResponseBuilder.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetWorkingOnResponse {
    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetEmployeeResponse employee;
    GetProjectResponse project;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetWorkingOnResponseBuilder {
    }
}
