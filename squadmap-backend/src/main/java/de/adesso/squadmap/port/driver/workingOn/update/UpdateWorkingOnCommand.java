package de.adesso.squadmap.port.driver.workingOn.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkingOnCommand {

    private long employeeId;
    private long projectId;
    @NotNull
    private LocalDate since;
    @NotNull
    private LocalDate until;
}
