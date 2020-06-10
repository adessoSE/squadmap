package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;

import java.util.List;

public interface ProjectResponseMapper {

    GetProjectResponse mapToResponseEntity(Project project, List<WorkingOn> workingOns);
}
