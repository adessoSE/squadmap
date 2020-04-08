package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GetWorkingOnAdapter implements GetWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> workingOnPersistenceMapper;

    @Override
    public WorkingOn getWorkingOn(Long workingOnId) {
        return workingOnPersistenceMapper.mapToDomainEntity(workingOnRepository.findById(workingOnId, 0)
                .orElseThrow(() -> new WorkingOnNotFoundException(workingOnId)));
    }
}
