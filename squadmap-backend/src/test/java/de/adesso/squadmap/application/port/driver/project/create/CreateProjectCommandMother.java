package de.adesso.squadmap.application.port.driver.project.create;

import java.time.LocalDate;
import java.util.ArrayList;

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
