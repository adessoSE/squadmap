package de.adesso.squadmap.application.port.driver.project.create;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateProjectCommandMother {

    public static CreateProjectCommand.CreateProjectCommandBuilder complete() {
        return CreateProjectCommand.builder()
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>());
    }
}
