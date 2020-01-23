package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final ListWorkingOnPort listWorkingOnPort;

    @Override
    public GetProjectResponse getProject(Long projectId) {
        return GetProjectResponse.getInstance(
                getProjectPort.getProject(projectId),
                listWorkingOnPort.listWorkingOn());
    }
}
