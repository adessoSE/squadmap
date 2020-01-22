package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final CreateProjectPort createProjectPort;
    private final Mapper<CreateProjectCommand, Project> projectMapper;

    public CreateProjectService(CreateProjectPort createProjectPort, Mapper<CreateProjectCommand, Project> projectMapper) {
        this.createProjectPort = createProjectPort;
        this.projectMapper = projectMapper;
    }

    @Override
    public Long createProject(CreateProjectCommand command) {
        return createProjectPort.createProject(projectMapper.map(command));
    }
}
