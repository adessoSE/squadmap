package de.adesso.squadmap.application.port.driver.project.create;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CreateProjectUseCase {

    Long createProject(@Valid CreateProjectCommand command);
}
