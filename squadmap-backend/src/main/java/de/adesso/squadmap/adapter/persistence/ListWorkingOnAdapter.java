package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
class ListWorkingOnAdapter implements ListWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final WorkingOnPersistenceMapper mapper;

    @Override
    public List<WorkingOn> listWorkingOn() {
        List<WorkingOn> workingOns = new ArrayList<>(Collections.emptyList());
        workingOnRepository.findAll().forEach(workingOnNeo4JEntity ->
                workingOns.add(mapper.mapToDomainEntity(workingOnNeo4JEntity)));
        return workingOns;
    }
}
