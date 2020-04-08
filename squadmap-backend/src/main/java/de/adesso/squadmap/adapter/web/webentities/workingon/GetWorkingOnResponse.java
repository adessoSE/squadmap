package de.adesso.squadmap.adapter.web.webentities.workingon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.adapter.web.webentities.employee.GetEmployeeResponse;
import de.adesso.squadmap.adapter.web.webentities.project.GetProjectResponse;
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
