package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.port.driven.workingon.DeleteWorkingOnPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import de.adesso.squadmap.domain.exceptions.WorkingOnNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class DeleteWorkingOnAdapter implements DeleteWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;

    @Override
    public void deleteWorkingOn(Long workingOnId) {
        if (!workingOnRepository.existsById(workingOnId)) {
            throw new WorkingOnNotFoundException(workingOnId);
        }
        workingOnRepository.deleteById(workingOnId);
    }
}
