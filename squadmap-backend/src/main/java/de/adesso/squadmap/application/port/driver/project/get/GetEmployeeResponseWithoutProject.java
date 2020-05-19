package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Employee;
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

    static GetEmployeeResponseWithoutProject of(Employee employee) {
        return GetEmployeeResponseWithoutProject.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthday(employee.getBirthday())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .image(employee.getImage())
                .isExternal(employee.getIsExternal())
                .build();
    }
}
