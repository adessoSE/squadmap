package de.adesso.squadmap.adapter.web.webentities.workingon;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import lombok.Data;

@Data
public class UpdateWorkingOnRequest {

    @JsonUnwrapped
    private UpdateWorkingOnCommand command;
}
