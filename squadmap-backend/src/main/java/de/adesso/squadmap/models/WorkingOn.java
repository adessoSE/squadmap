package de.adesso.squadmap.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@Data
@RelationshipEntity(type = "WORKING_ON")
@AllArgsConstructor
public class WorkingOn {

    @Id
    @GeneratedValue
    private long workingOnId;
    private LocalDate since;
    private LocalDate until;

    @StartNode
    private Employee employee;
    @EndNode
    private Project project;

    public WorkingOn(){}
}
