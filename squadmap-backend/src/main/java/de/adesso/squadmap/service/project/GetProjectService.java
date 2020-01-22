package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driven.project.GetProjectPort;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final Mapper<Project, GetProjectResponse> projectMapper;

    public GetProjectService(GetProjectPort getProjectPort, Mapper<Project, GetProjectResponse> projectMapper) {
        this.getProjectPort = getProjectPort;
        this.projectMapper = projectMapper;
    }

    @Override
    public GetProjectResponse getProject(Long projectId) {
        return projectMapper.map(getProjectPort.getProject(projectId));
    }
}
