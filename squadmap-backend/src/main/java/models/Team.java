package models;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private long teamId;
    private String name;
    private Employee teamLeader;
    private List<Employee> employees;
}
