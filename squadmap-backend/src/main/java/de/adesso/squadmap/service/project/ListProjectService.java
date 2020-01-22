package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driven.project.ListProjectPort;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListProjectService implements ListProjectUseCase {

    private final ListProjectPort listProjectPort;
    private final Mapper<Project, GetProjectResponse> projectMapper;

    public ListProjectService(ListProjectPort listProjectPort, Mapper<Project, GetProjectResponse> projectMapper){
        this.listProjectPort = listProjectPort;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<GetProjectResponse> listProjects() {
        List<GetProjectResponse> responses = new ArrayList<>();
        listProjectPort.listProjects().forEach(project -> responses.add(projectMapper.map(project)));
        return responses;
    }
}
