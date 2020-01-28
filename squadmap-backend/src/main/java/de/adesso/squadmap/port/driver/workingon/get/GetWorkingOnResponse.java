package de.adesso.squadmap.port.driver.workingon.get;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkingOnResponse {
    private Long workingOnId;
    private GetEmployeeResponse employee;
    private GetProjectResponse project;
    private LocalDate since;
    private LocalDate until;
    private int workload;
}