package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GetProjectAdapter implements GetProjectPort {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    public Project getProject(Long projectId) {
        return mapper.mapToDomainEntity(projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new));
    }
}
