package de.adesso.squadmap.application.port.driver.project.get;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
class GetWorkingOnResponseWithoutProject {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetEmployeeResponseWithoutProject employee;
}
