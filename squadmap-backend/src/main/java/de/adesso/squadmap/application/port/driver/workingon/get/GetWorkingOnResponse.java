package de.adesso.squadmap.application.port.driver.workingon.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderClassName = "GetWorkingOnResponseBuilder")
@JsonDeserialize(builder = GetWorkingOnResponse.GetWorkingOnResponseBuilder.class)
public class GetWorkingOnResponse {
    private final Long workingOnId;
    private final GetEmployeeResponse employee;
    private final GetProjectResponse project;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetWorkingOnResponseBuilder { }
}
