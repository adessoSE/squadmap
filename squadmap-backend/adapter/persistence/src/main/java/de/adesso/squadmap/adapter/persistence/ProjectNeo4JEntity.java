package de.adesso.squadmap.adapter.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.List;

@Data
@NodeEntity(label = "Project")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ProjectNeo4JEntity {

    @Id
    @GeneratedValue
    private Long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private Boolean isExternal;
    private List<String> sites;

    @Relationship(type = "WORKING_ON", direction = Relationship.UNDIRECTED)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<WorkingOnNeo4JEntity> employees;
}
