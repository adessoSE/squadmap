package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;

import java.util.List;

public interface ProjectResponseMapper {

    GetProjectResponse mapToResponseEntity(Project project, List<WorkingOn> workingOns);
}
