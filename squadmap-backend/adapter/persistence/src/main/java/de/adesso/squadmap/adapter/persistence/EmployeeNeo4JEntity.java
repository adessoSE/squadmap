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
@NodeEntity(label = "Employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EmployeeNeo4JEntity {

    @Id
    @GeneratedValue
    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Boolean isExternal;
    private String image;

    @Relationship(type = "WORKING_ON", direction = Relationship.UNDIRECTED)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<WorkingOnNeo4JEntity> projects;
}
