package de.adesso.squadmap.adapter.workingon;

import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driven.workingon.DeleteWorkingOnPort;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteWorkingOnAdapter implements DeleteWorkingOnPort {

    private WorkingOnRepository workingOnRepository;

    public DeleteWorkingOnAdapter(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void deleteWorkingOn(Long workingOnId) {
        if (!workingOnRepository.existsById(workingOnId)) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.deleteById(workingOnId);
    }
}
