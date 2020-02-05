package de.adesso.squadmap.application.port.driver.workingon;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public abstract class WorkingOnCommand {

    private final long employeeId;
    private final long projectId;
    @NotNull
    private final LocalDate since;
    @NotNull
    private final LocalDate until;
    @NotNull
    @Range(min = 0, max = 100)
    private final Integer workload;
}
