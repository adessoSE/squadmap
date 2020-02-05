package de.adesso.squadmap.application.port.driver.project.update;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateProjectCommandMother {

    public static UpdateProjectCommand.UpdateProjectCommandBuilder complete() {
        return UpdateProjectCommand.builder()
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>());
    }
}
