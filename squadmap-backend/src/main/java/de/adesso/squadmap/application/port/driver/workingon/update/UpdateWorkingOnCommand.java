package de.adesso.squadmap.application.port.driver.workingon.update;

import de.adesso.squadmap.application.port.driver.workingon.WorkingOnCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateWorkingOnCommand extends WorkingOnCommand {

    @Builder
    UpdateWorkingOnCommand(
            long employeeId,
            long projectId,
            LocalDate since,
            LocalDate until,
            Integer workload) {
        super(employeeId, projectId, since, until, workload);
    }
}
