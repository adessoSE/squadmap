package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @JsonUnwrapped
    private CreateProjectCommand command;
}
