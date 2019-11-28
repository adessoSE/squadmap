package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Employee {
    @Id @GeneratedValue
    private long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private boolean isExternal;

    @Relationship(type = "WORKING_ON", direction = Relationship.OUTGOING)
    @EqualsAndHashCode.Exclude
    private List<WorkingOn> projects = new ArrayList<>();

    public Employee(){}
}
