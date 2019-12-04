package de.adesso.squadmap.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDate;

@Data
@RelationshipEntity(type = "WORKING_ON")
public class WorkingOn {

    private LocalDate since;
    private LocalDate until;

    @StartNode
    private Employee employee;
    @EndNode
    private Project project;
}
