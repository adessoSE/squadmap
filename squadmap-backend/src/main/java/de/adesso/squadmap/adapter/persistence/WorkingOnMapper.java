package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WorkingOnMapper {

    private final EmployeeMapper employeeMapper;
    private final ProjectMapper projectMapper;

    WorkingOnNeo4JEntity mapToNeo4JEntity(WorkingOn workingOn) {
        return new WorkingOnNeo4JEntity(
                workingOn.getWorkingOnId(),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload(),
                employeeMapper.mapToNeo4JEntity(workingOn.getEmployee()),
                projectMapper.mapToNeo4JEntity(workingOn.getProject()));
    }

    WorkingOn mapToDomainEntity(WorkingOnNeo4JEntity workingOnNeo4JEntity) {
        return WorkingOn.withId(
                workingOnNeo4JEntity.getWorkingOnId(),
                workingOnNeo4JEntity.getSince(),
                workingOnNeo4JEntity.getUntil(),
                workingOnNeo4JEntity.getWorkload(),
                employeeMapper.mapToDomainEntity(workingOnNeo4JEntity.getEmployee()),
                projectMapper.mapToDomainEntity(workingOnNeo4JEntity.getProject()));
    }
}
