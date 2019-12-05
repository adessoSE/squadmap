package de.adesso.squadmap.port.driver.workingOn.create;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkingOnCommand {

    private long employeeId;
    private long projectId;
    private LocalDate since;
    private LocalDate until;
}
