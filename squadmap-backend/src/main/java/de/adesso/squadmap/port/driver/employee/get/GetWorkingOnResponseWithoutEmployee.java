package de.adesso.squadmap.port.driver.employee.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkingOnResponseWithoutEmployee {

    private Long workingOnId;
    private GetProjectResponseWithoutEmployee project;
    private LocalDate since;
    private LocalDate until;
    private int workload;
}

