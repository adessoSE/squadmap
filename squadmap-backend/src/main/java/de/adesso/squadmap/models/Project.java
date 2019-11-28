package de.adesso.squadmap.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NodeEntity
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    private long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private boolean isExternal;
    @Relationship(type ="Working_ON", direction = Relationship.INCOMING)
    @EqualsAndHashCode.Exclude
    private List<WorkingOn> employees = new ArrayList<>();

    public Project(){}
}
