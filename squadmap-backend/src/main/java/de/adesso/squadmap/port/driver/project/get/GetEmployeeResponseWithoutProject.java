package de.adesso.squadmap.port.driver.project.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponseWithoutProject {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Boolean isExternal;
    private String image;
}
