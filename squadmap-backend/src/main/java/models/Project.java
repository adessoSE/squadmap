package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.LocalDate;
import java.util.List;


@Data
@NodeEntity
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    boolean isExternal;

    List<Employee> employees; //changing Employee to the specific relation

    public Project(){}
}
