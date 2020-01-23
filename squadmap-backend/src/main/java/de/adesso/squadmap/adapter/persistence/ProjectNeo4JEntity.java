package de.adesso.squadmap.adapter.persistence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NodeEntity(label = "Project")
@RequiredArgsConstructor
class ProjectNeo4JEntity {

    @Id
    @GeneratedValue
    private final Long projectId;
    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List<String> sites;

    @Relationship(type = "WORKING_ON", direction = Relationship.INCOMING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WorkingOnNeo4JEntity> employees = new ArrayList<>();

    ProjectNeo4JEntity(
            String title,
            String description,
            LocalDate since,
            LocalDate until,
            boolean isExternal,
            List<String> sites) {
        this.projectId = null;
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = sites;
    }
}
