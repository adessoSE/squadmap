package models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final boolean isExternal;

    @Relationship(type = "WORKING_ON", direction = Relationship.OUTGOING)
    @EqualsAndHashCode.Exclude
    private List<WorkingOn> projects = new ArrayList<>();
}


