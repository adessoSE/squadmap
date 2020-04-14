package de.adesso.squadmap.application.port.driver.project.create;

import de.adesso.squadmap.application.port.driver.project.ProjectCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateProjectCommand extends ProjectCommand {

    @Builder
    CreateProjectCommand(
            String title,
            String description,
            LocalDate since,
            LocalDate until,
            Boolean isExternal,
            List<String> sites) {
        super(title, description, since, until, isExternal, sites);
    }
}

