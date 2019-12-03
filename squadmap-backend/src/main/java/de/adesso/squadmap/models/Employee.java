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
public class Employee {
    @Id
    @GeneratedValue
    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Boolean isExternal;

    @JsonIgnoreProperties("employee")
    @Relationship(type = "WORKING_ON")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<WorkingOn> projects = new ArrayList<>();

    public Employee(String firstName, String lastName, LocalDate birthday, String email, String phone, boolean isExternal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.isExternal = isExternal;
    }
}


