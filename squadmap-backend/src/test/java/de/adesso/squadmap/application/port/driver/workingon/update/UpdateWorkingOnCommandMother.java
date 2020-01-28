package de.adesso.squadmap.application.port.driver.workingon.update;

import java.time.LocalDate;

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
