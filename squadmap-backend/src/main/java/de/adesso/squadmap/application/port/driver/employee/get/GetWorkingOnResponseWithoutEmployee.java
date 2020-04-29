package de.adesso.squadmap.application.port.driver.employee.get;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
class GetWorkingOnResponseWithoutEmployee {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetProjectResponseWithoutEmployee project;
}

