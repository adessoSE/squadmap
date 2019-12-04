package de.adesso.squadmap.port.driver.workingOn.get;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkingOnResponse {

    private Long workingOnId;
    private Employee employee;
    private Project project;
    private LocalDate since;
    private LocalDate until;
}
