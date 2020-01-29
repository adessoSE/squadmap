package de.adesso.squadmap.application.port.driver.employee.get;

import lombok.Value;

import java.time.LocalDate;

@Value
class GetWorkingOnResponseWithoutEmployee {

    Long workingOnId;
    GetProjectResponseWithoutEmployee project;
    LocalDate since;
    LocalDate until;
    Integer workload;
}

