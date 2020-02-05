package de.adesso.squadmap.application.domain;

import java.time.LocalDate;

public class WorkingOnMother {

    public static WorkingOn.WorkingOnBuilder complete() {
        return WorkingOn.builder()
                .workingOnId(1L)
                .employee(EmployeeMother.complete().build())
                .project(ProjectMother.complete().build())
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0);
    }
}
