package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.port.driven.workingon.DeleteWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DeleteWorkingOnAdapter implements DeleteWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;

    @Override
    public void deleteWorkingOn(Long workingOnId) {
        if (!workingOnRepository.existsById(workingOnId)) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.deleteById(workingOnId);
    }
}
