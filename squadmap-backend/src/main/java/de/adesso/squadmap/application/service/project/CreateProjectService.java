package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.ProjectDomainMapper;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class CreateProjectService implements CreateProjectUseCase {

    private final CreateProjectPort createProjectPort;
    private final ProjectDomainMapper projectMapper;

    @Override
    @Transactional
    public Long createProject(CreateProjectCommand command) {
        return createProjectPort.createProject(projectMapper.mapToDomainEntity(command));
    }
}
