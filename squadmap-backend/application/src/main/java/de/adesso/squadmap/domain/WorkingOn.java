package de.adesso.squadmap.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@RequiredArgsConstructor
public class WorkingOn {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    Employee employee;
    Project project;
}
