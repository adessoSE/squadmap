package de.adesso.squadmap.application.port.driver.employee.get;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class GetEmployeeResponse {

    Long employeeId;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    String phone;
    Boolean isExternal;
    String image;
    List<GetWorkingOnResponseWithoutEmployee> projects;
}
