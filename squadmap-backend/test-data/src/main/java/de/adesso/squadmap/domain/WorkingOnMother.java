package de.adesso.squadmap.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
