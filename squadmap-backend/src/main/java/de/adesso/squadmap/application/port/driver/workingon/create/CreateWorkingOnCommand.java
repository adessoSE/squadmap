package de.adesso.squadmap.application.port.driver.workingon.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.workingon.WorkingOnCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonDeserialize(builder = CreateProjectCommand.CreateProjectCommandBuilder.class)
public class CreateWorkingOnCommand extends WorkingOnCommand {

    @Builder
    @JsonCreator
    public CreateWorkingOnCommand(long employeeId, long projectId, LocalDate since, LocalDate until, int workload) {
        super(employeeId, projectId, since, until, workload);
    }
}
