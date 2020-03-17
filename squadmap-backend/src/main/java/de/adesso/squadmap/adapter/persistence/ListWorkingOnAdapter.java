package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
class ListWorkingOnAdapter implements ListWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> mapper;

    @Override
    public List<WorkingOn> listWorkingOn() {
        return StreamSupport.stream(workingOnRepository.findAll().spliterator(), false)
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkingOn> listWorkingOnByEmployeeId(long employeeId) {
        return StreamSupport.stream(workingOnRepository.findAllByEmployeeId(employeeId).spliterator(), false)
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkingOn> listWorkingOnByProjectId(long projectId) {
        return StreamSupport.stream(workingOnRepository.findAllByProjectId(projectId).spliterator(), false)
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
