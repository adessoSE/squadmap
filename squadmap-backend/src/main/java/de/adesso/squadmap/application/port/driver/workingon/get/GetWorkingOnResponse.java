package de.adesso.squadmap.application.port.driver.workingon.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "GetWorkingOnResponseBuilder")
@JsonDeserialize(builder = GetWorkingOnResponse.GetWorkingOnResponseBuilder.class)
public class GetWorkingOnResponse {
    private final Long workingOnId;
    private final GetEmployeeResponse employee;
    private final GetProjectResponse project;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;

    public static GetWorkingOnResponse getInstance(WorkingOn workingOn, List<WorkingOn> workingOns){
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                GetEmployeeResponse.getInstance(workingOn.getEmployee(), workingOns),
                GetProjectResponse.getInstance(workingOn.getProject(), workingOns),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseBuilder { }
}
