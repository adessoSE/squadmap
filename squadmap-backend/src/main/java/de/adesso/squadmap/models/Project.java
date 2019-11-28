package de.adesso.squadmap.models;

import lombok.*;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    private Long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private Boolean isExternal;

    @Relationship(type ="Working_ON", direction = Relationship.INCOMING)
    @EqualsAndHashCode.Exclude
    private List<WorkingOn> employees = new ArrayList<>();

    public Project(String title, String description, LocalDate since, LocalDate until, boolean isExternal){
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
    }
}
