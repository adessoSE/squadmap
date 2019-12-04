package de.adesso.squadmap.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "WORKING_ON")
public class WorkingOn {

    private LocalDate since;
    private LocalDate until;

    @StartNode
    private Employee employee;
    @EndNode
    private Project project;

    public WorkingOn(
            LocalDate since,
            LocalDate until,
            Employee employee,
            Project project) {
        this.since = since;
        this.until = until;
        this.employee = employee;
        this.project = project;
    }
}
