package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import lombok.Data;

@Data
public class UpdateProjectRequest {

    @JsonUnwrapped
    private UpdateProjectCommand command;
}
