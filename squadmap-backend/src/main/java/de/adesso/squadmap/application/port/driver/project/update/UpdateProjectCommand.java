package de.adesso.squadmap.application.port.driver.project.update;

import de.adesso.squadmap.application.port.driver.project.ProjectCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateProjectCommand extends ProjectCommand {

    @Builder
    public UpdateProjectCommand(String title, String description, LocalDate since, LocalDate until, boolean isExternal, List<String> sites) {
        super(title, description, since, until, isExternal, sites);
    }
}
