package de.adesso.squadmap.domain;

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
@NoArgsConstructor
@NodeEntity
public class Project {

    @Id
    @GeneratedValue
    private Long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private Boolean isExternal;
    private List<String> sites;

    @Relationship(type = "WORKING_ON", direction = Relationship.INCOMING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WorkingOn> employees = new ArrayList<>();

    public Project(
            String title,
            String description,
            LocalDate since,
            LocalDate until,
            boolean isExternal,
            List<String> sites) {
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = sites;
    }
}
