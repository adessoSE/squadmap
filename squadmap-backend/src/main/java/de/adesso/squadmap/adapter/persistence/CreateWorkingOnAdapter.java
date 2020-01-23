package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateWorkingOnAdapter implements CreateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final WorkingOnMapper mapper;

    @Override
    public long createWorkingOn(WorkingOn workingOn) {
        if (workingOnRepository.existsByEmployeeAndProject(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        return workingOnRepository.save(mapper.mapToNeo4JEntity(workingOn)).getWorkingOnId();
    }
}
