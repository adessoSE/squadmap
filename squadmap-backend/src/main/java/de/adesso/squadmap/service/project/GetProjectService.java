package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {


    @Override
    public GetProjectResponse getProject(Long projectId) {
        return null;
    }
}
