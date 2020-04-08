package de.adesso.squadmap.adapter.web.webentities.workingon;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import lombok.Data;

@Data
public class CreateWorkingOnRequest {

    @JsonUnwrapped
    private CreateWorkingOnCommand command;
}
