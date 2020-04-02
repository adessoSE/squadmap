package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;

public interface GetProjectUseCase {

    Project getProject(Long projectId);
}
