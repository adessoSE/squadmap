package de.adesso.squadmap.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "WORKING_ON")
public class WorkingOn {

    @Id
    @GeneratedValue
    private Long workingOnId;
    private LocalDate since;
    private LocalDate until;
    private int workload;

    @StartNode
    private Employee employee;
    @EndNode
    private Project project;

    public WorkingOn(
            Employee employee,
            Project project,
            LocalDate since,
            LocalDate until,
            int workload
    ) {
        this.employee = employee;
        this.project = project;
        this.since = since;
        this.until = until;
        this.workload = workload;
    }
}
