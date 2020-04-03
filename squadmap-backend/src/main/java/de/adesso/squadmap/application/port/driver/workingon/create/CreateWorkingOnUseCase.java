package de.adesso.squadmap.application.port.driver.workingon.create;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CreateWorkingOnUseCase {

    Long createWorkingOn(@Valid CreateWorkingOnCommand command);
}
