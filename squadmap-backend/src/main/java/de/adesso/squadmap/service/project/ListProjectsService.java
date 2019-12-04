package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsService implements ListProjectUseCase {


    @Override
    public List<GetProjectResponse> listProjects() {
        return null;
    }
}
