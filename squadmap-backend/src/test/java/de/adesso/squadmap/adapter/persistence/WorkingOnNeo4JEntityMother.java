package de.adesso.squadmap.adapter.persistence;

import java.time.LocalDate;

class WorkingOnNeo4JEntityMother {

    static WorkingOnNeo4JEntity.WorkingOnNeo4JEntityBuilder complete() {
        return WorkingOnNeo4JEntity.builder()
                .workingOnId(1L)
                .employee(EmployeeNeo4JEntityMother.complete().build())
                .project(ProjectNeo4JEntityMother.complete().build())
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0);
    }
}
