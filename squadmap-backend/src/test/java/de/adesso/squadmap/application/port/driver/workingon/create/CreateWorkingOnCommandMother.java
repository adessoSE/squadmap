package de.adesso.squadmap.application.port.driver.workingon.create;

import java.time.LocalDate;

public class CreateWorkingOnCommandMother {

    public static CreateWorkingOnCommand.CreateWorkingOnCommandBuilder complete() {
        return CreateWorkingOnCommand.builder()
                .employeeId(1L)
                .projectId(1L)
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0);
    }
}
