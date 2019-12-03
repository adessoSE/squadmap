package de.adesso.squadmap.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@Data
@RelationshipEntity(type = "WORKING_ON")
@NoArgsConstructor
public class WorkingOn {

    @Id
    @GeneratedValue
    private Long workingOnId;
    private LocalDate since;
    private LocalDate until;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @StartNode
    private Employee employee;
    @EndNode
    private Project project;

    public WorkingOn(Employee employee, Project project, LocalDate since, LocalDate until){
        this.employee = employee;
        this.project = project;
        this.since = since;
        this.until = until;
    }
}
