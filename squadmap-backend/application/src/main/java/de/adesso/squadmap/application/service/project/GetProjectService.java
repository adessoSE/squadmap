package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.domain.mapper.ProjectResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final ProjectResponseMapper projectResponseMapper;

    @Override
    @Transactional
    public GetProjectResponse getProject(Long projectId) {
        return projectResponseMapper.mapToResponseEntity(
                getProjectPort.getProject(projectId),
                listWorkingOnPort.listWorkingOnByProjectId(projectId));
    }
}
