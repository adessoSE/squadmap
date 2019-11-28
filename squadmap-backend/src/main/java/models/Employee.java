package models;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Employee {
    private long employeeId;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String email;
    private String phone;
    private boolean isExternal;
    private List<Team> teams;
}
