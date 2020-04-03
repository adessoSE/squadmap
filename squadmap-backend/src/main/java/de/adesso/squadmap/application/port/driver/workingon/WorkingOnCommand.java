package de.adesso.squadmap.application.port.driver.workingon;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public abstract class WorkingOnCommand {

    private final long employeeId;
    private final long projectId;
    @NotNull(message = "should not be null")
    private final LocalDate since;
    @NotNull(message = "should not be null")
    private final LocalDate until;
    @NotNull(message = "should not be null")
    @Range(min = 0, max = 100, message = "should be a number between {min} and {max}")
    private final Integer workload;
}
