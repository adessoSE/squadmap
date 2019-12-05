package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.utility.ProjectMapper;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectService implements CreateProjectUseCase {

    private ProjectRepository projectRepository;

    public CreateProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public Long createProject(CreateProjectCommand command) {
        Project project = ProjectMapper.mapCreateProjectCommandToProject(command);
        return projectRepository.save(project).getProjectId();
    }
}
