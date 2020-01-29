package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.Value;

import java.time.LocalDate;

@Value
public class GetWorkingOnResponse {
    Long workingOnId;
    GetEmployeeResponse employee;
    GetProjectResponse project;
    LocalDate since;
    LocalDate until;
    Integer workload;
}
