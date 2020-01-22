package de.adesso.squadmap.adapter.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateWorkingOnAdapter implements UpdateWorkingOnPort {

    private WorkingOnRepository workingOnRepository;

    public UpdateWorkingOnAdapter(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void updateWorkingOn(WorkingOn workingOn) {
        if (!workingOnRepository.existsById(workingOn.getWorkingOnId())) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.save(workingOn);
    }
}
