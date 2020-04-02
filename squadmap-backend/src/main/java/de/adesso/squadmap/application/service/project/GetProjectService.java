package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetProjectService implements GetProjectUseCase {

    private final GetProjectPort getProjectPort;

    @Override
    @Transactional
    public Project getProject(Long projectId) {
        return getProjectPort.getProject(projectId);
    }
}
