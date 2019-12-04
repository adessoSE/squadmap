package de.adesso.squadmap.service.project;

import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectService implements CreateProjectUseCase {


    @Override
    public Long createProject(CreateProjectCommand command) {
        return null;
    }
}
