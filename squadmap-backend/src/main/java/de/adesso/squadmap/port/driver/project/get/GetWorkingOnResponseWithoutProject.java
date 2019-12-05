package de.adesso.squadmap.port.driver.project.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkingOnResponseWithoutProject {

    private Long workingOnId;
    private GetEmployeeResponseWithoutProject employee;
    private LocalDate since;
    private LocalDate until;
}
