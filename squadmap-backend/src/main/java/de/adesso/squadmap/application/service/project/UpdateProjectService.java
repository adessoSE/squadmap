package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.mapper.ProjectDomainMapper;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort updateProjectPort;
    private final ProjectDomainMapper projectMapper;

    @Override
    @Transactional
    public void updateProject(UpdateProjectCommand command, Long projectId) {
        updateProjectPort.updateProject(projectMapper.mapToDomainEntity(command, projectId));
    }
}
