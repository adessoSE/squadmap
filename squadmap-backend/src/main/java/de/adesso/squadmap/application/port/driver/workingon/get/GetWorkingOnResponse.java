package de.adesso.squadmap.application.port.driver.workingon.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "GetWorkingOnResponseBuilder")
@JsonDeserialize(builder = GetWorkingOnResponse.GetWorkingOnResponseBuilder.class)
public class GetWorkingOnResponse {
    Long workingOnId;
    GetEmployeeResponse employee;
    GetProjectResponse project;
    LocalDate since;
    LocalDate until;
    Integer workload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetWorkingOnResponseBuilder {
    }
}
