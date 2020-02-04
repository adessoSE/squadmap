package de.adesso.squadmap.application.port.driver.workingon.update;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "UpdateWorkingOnCommandBuilder")
@JsonDeserialize(builder = UpdateWorkingOnCommand.UpdateWorkingOnCommandBuilder.class)
public class UpdateWorkingOnCommand {

    private final long employeeId;
    private final long projectId;
    @NotNull
    private final LocalDate since;
    @NotNull
    private final LocalDate until;
    @Range(min = 0, max = 100)
    private final int workload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UpdateWorkingOnCommandBuilder { }
}
