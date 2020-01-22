package de.adesso.squadmap.adapter.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Component;

@Component
public class GetWorkingOnAdapter implements GetWorkingOnPort {

    private WorkingOnRepository workingOnRepository;

    public GetWorkingOnAdapter(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public WorkingOn getWorkingOn(Long workingOnId) {
        return workingOnRepository.findById(workingOnId).orElseThrow(WorkingOnNotFoundException::new);
    }
}
