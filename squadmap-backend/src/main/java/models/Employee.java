package models;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Employee {
    private long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private boolean isExternal;
    private List<Project> projects;
}
