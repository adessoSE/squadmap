package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.port.driven.project.DeleteProjectPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import de.adesso.squadmap.domain.exceptions.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class DeleteProjectAdapter implements DeleteProjectPort {

    private final ProjectRepository projectRepository;

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }
        projectRepository.deleteById(projectId);
    }
}
