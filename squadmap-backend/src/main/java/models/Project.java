package models;

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
    long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    boolean isExternal;
    @Relationship(type ="Working_ON", direction = Relationship.INCOMING)
    @EqualsAndHashCode.Exclude
    List<WorkingOn> employees = new ArrayList<>();

    public Project(){}
}
