package de.adesso.squadmap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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

    @JsonIgnoreProperties("project")
    @Relationship(type = "WORKING_ON", direction = Relationship.INCOMING)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<WorkingOn> employees = new ArrayList<>();

    public Project(String title, String description, LocalDate since, LocalDate until, boolean isExternal) {
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
    }
}
