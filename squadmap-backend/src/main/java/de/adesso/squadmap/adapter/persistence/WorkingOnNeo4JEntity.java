package de.adesso.squadmap.adapter.persistence;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@Data
@RelationshipEntity(type = "WORKING_ON")
@RequiredArgsConstructor
class WorkingOnNeo4JEntity {

    @Id
    @GeneratedValue
    private final Long workingOnId;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;

    @StartNode
    private final EmployeeNeo4JEntity employee;
    @EndNode
    private final ProjectNeo4JEntity project;

    WorkingOnNeo4JEntity(
            LocalDate since,
            LocalDate until,
            int workload,
            EmployeeNeo4JEntity employee,
            ProjectNeo4JEntity project
    ) {
        this.workingOnId = null;
        this.since = since;
        this.until = until;
        this.workload = workload;
        this.employee = employee;
        this.project = project;
    }
}
