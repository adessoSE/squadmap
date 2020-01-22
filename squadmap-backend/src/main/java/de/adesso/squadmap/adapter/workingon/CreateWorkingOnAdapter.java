package de.adesso.squadmap.adapter.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkingOnAdapter implements CreateWorkingOnPort {

    private WorkingOnRepository workingOnRepository;

    public CreateWorkingOnAdapter(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public long createWorkingOn(WorkingOn workingOn) {
        if (workingOnRepository.existsByEmployeeAndProject(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        return workingOnRepository.save(workingOn).getWorkingOnId();
    }
}
