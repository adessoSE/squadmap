package de.adesso.squadmap.application.port.driver.project.update;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UpdateProjectUseCase {

    void updateProject(@Valid UpdateProjectCommand command, Long projectId);
}
