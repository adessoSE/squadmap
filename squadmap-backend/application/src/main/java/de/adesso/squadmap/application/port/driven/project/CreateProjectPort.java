package de.adesso.squadmap.application.port.driven.project;

import de.adesso.squadmap.domain.Project;

public interface CreateProjectPort {

    long createProject(Project project);
}
