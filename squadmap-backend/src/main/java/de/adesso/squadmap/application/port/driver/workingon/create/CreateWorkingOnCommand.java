package de.adesso.squadmap.application.port.driver.workingon.create;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "CreateWorkingOnCommandBuilder")
@JsonDeserialize(builder = CreateWorkingOnCommand.CreateWorkingOnCommandBuilder.class)
public class CreateWorkingOnCommand {

    private final long employeeId;
    private final long projectId;
    @NotNull
    private final LocalDate since;
    @NotNull
    private final LocalDate until;
    @Range(min = 0, max = 100)
    private final int workload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CreateWorkingOnCommandBuilder { }
}
