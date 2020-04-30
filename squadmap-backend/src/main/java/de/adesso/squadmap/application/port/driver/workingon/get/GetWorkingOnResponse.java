package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class GetWorkingOnResponse {
    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetEmployeeResponse employee;
    GetProjectResponse project;

    private GetWorkingOnResponse(Long workingOnId,
                                 LocalDate since,
                                 LocalDate until,
                                 Integer workload,
                                 GetEmployeeResponse employee,
                                 GetProjectResponse project) {
        this.workingOnId = workingOnId;
        this.since = since;
        this.until = until;
        this.workload = workload;
        this.employee = employee;
        this.project = project;
    }
}
