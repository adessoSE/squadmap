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
public class Employee {

    @Id@GeneratedValue
    Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Boolean isExternal;

    @Relationship(type = "WORKING_ON")
    private List<WorkingOn> projects;
}
