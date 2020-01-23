package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdateWorkingOnAdapter implements UpdateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final WorkingOnMapper mapper;

    @Override
    public void updateWorkingOn(WorkingOn workingOn) {
        if (!workingOnRepository.existsById(workingOn.getWorkingOnId())) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.save(mapper.mapToNeo4JEntity(workingOn));
    }
}
