package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final ResponseMapper<Project, GetProjectResponse> projectResponseMapper;

    @Override
    @Transactional
    public GetProjectResponse getProject(Long projectId) {
        return projectResponseMapper.toResponse(
                getProjectPort.getProject(projectId),
                listWorkingOnPort.listWorkingOnByProjectId(projectId));
    }
}
