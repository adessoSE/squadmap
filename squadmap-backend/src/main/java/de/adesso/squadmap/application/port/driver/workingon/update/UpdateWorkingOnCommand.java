package de.adesso.squadmap.application.port.driver.workingon.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.adesso.squadmap.application.port.driver.workingon.WorkingOnCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonDeserialize(builder = UpdateWorkingOnCommand.UpdateWorkingOnCommandBuilder.class)
public class UpdateWorkingOnCommand extends WorkingOnCommand {

    @Builder
    @JsonCreator
    public UpdateWorkingOnCommand(long employeeId, long projectId, LocalDate since, LocalDate until, int workload) {
        super(employeeId, projectId, since, until, workload);
    }
}
