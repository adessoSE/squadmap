package de.adesso.squadmap.service.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.repository.ProjectRepository;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectService implements CreateProjectUseCase {

    private ProjectRepository projectRepository;

    public CreateProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public Long createProject(CreateProjectCommand command) {
        Project project = new Project(
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.getIsExternal());
        return projectRepository.save(project).getProjectId();
    }
}
