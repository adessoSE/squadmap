package de.adesso.squadmap.application.port.driver.workingon.update;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateWorkingOnCommandMother {

    public static UpdateWorkingOnCommand.UpdateWorkingOnCommandBuilder complete() {
        return UpdateWorkingOnCommand.builder()
                .employeeId(1L)
                .projectId(1L)
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0);
    }
}
