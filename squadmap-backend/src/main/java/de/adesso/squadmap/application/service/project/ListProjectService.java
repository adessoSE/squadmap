package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ListProjectService implements ListProjectUseCase {

    private final ListProjectPort listProjectPort;

    @Override
    @Transactional
    public List<Project> listProjects() {
        return listProjectPort.listProjects();
    }
}
