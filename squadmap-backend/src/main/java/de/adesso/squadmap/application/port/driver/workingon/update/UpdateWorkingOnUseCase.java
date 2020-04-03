package de.adesso.squadmap.application.port.driver.workingon.update;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UpdateWorkingOnUseCase {

    void updateWorkingOn(@Valid UpdateWorkingOnCommand command, Long workingOnId);
}
