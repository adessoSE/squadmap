package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final Mapper<CreateProjectCommand, Project> projectMapper;

    public CreateProjectService(ProjectRepository projectRepository, Mapper<CreateProjectCommand, Project> projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public Long createProject(CreateProjectCommand command) {
        if (projectRepository.existsByTitle(command.getTitle())) {
            throw new ProjectAlreadyExistsException();
        }
        Project project = projectMapper.map(command);
        return projectRepository.save(project).getProjectId();
    }
}
