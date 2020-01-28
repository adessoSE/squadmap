package de.adesso.squadmap.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@Data
@RelationshipEntity(type = "WORKING_ON")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class WorkingOnNeo4JEntity {

    @Id
    @GeneratedValue
    private Long workingOnId;
    private LocalDate since;
    private LocalDate until;
    private int workload;

    @StartNode
    private EmployeeNeo4JEntity employee;
    @EndNode
    private ProjectNeo4JEntity project;
}
