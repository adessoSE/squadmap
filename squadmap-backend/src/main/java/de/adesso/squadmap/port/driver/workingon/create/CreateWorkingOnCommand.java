package de.adesso.squadmap.port.driver.workingon.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkingOnCommand {

    private long employeeId;
    private long projectId;
    @NotNull
    private LocalDate since;
    @NotNull
    private LocalDate until;
    @Range(min = 0, max = 100)
    private int workload;
}
