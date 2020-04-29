package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.ProjectResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ProjectResponseMapperImplementation implements ProjectResponseMapper {

    @Override
    public GetProjectResponse mapToResponseEntity(Project project, List<WorkingOn> workingOns) {
        return GetProjectResponse.of(project, workingOns);
    }
}
