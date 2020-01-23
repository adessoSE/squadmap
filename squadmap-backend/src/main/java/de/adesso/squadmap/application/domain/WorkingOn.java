package de.adesso.squadmap.application.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkingOn {

    private final Long workingOnId;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;
    private final Employee employee;
    private final Project project;

    public static WorkingOn withId(
            long workingOnId,
            LocalDate since,
            LocalDate until,
            int workload,
            Employee employee,
            Project project) {
        return new WorkingOn(
                workingOnId,
                since,
                until,
                workload,
                employee,
                project);
    }

    public static WorkingOn withoutId(
            LocalDate since,
            LocalDate until,
            int workload,
            Employee employee,
            Project project) {
        return new WorkingOn(
                null,
                since,
                until,
                workload,
                employee,
                project);
    }
}
