package de.adesso.squadmap.application.port.driver.project.get;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
class GetEmployeeResponseWithoutProject {

    Long employeeId;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    String phone;
    Boolean isExternal;
    String image;
}
