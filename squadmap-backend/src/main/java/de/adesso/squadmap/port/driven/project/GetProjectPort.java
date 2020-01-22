package de.adesso.squadmap.port.driven.project;

import de.adesso.squadmap.domain.Project;

public interface GetProjectPort {

    Project getProject(Long projectId);
}
