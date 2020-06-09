package de.adesso.squadmap.application.port.driver.workingon.create;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
