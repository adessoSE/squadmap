package de.adesso.squadmap.adapter.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ListWorkingOnAdapter implements ListWorkingOnPort {

    private WorkingOnRepository workingOnRepository;

    public ListWorkingOnAdapter(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public List<WorkingOn> listWorkingOn() {
        return StreamSupport.stream(workingOnRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
