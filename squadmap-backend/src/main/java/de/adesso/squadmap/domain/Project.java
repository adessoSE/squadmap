package de.adesso.squadmap.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.List;

@Data
@NodeEntity
public class Project {

    @Id@GeneratedValue
    private Long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;

    @Relationship(type = "WORKING_ON", direction = Relationship.INCOMING)
    private List<WorkingOn> employees;

}
