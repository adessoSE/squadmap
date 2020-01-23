package de.adesso.squadmap.adapter.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity(label = "Employee")
@RequiredArgsConstructor
class EmployeeNeo4JEntity {

    @Id
    @GeneratedValue
    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final Boolean isExternal;
    private final String image;

    @Relationship(type = "WORKING_ON")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WorkingOnNeo4JEntity> projects = new ArrayList<>();

    EmployeeNeo4JEntity(
            String firstName,
            String lastName,
            LocalDate birthday,
            String email,
            String phone,
            boolean isExternal,
            String image) {
        this.employeeId = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.isExternal = isExternal;
        this.image = image;
    }
}
