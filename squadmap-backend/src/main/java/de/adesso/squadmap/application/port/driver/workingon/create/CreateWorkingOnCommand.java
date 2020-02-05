package de.adesso.squadmap.application.port.driver.workingon.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.adesso.squadmap.application.port.driver.workingon.WorkingOnCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateWorkingOnCommand extends WorkingOnCommand {

    @Builder
    @JsonCreator
    public CreateWorkingOnCommand(
            @JsonProperty("employeeId") long employeeId,
            @JsonProperty("projectId") long projectId,
            @JsonProperty("since") LocalDate since,
            @JsonProperty("until") LocalDate until,
            @JsonProperty("workload") Integer workload) {
        super(employeeId, projectId, since, until, workload);
    }
}
