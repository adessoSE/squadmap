package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.exceptions.WorkingOnNotFoundException;
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
