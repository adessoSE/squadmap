package de.adesso.squadmap.application.port.driver.workingon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class WorkingOnCommand {

    private final long employeeId;
    private final long projectId;
    @NotNull
    private final LocalDate since;
    @NotNull
    private final LocalDate until;
    @Range(min = 0, max = 100)
    private final int workload;
}
