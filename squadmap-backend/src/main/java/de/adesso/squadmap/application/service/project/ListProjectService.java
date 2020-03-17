package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ListProjectService implements ListProjectUseCase {

    private final ListProjectPort listProjectPort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final ResponseMapper<Project, GetProjectResponse> projectResponseMapper;

    @Override
    @Transactional
    public List<GetProjectResponse> listProjects() {
        List<WorkingOn> allRelations = listWorkingOnPort.listWorkingOn();
        return listProjectPort.listProjects().stream()
                .map(project -> projectResponseMapper.toResponse(project, allRelations))
                .collect(Collectors.toList());
    }
}
