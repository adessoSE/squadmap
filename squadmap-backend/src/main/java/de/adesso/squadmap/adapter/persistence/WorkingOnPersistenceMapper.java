package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WorkingOnPersistenceMapper {

    private final EmployeePersistenceMapper employeePersistenceMapper;
    private final ProjectPersistenceMapper projectPersistenceMapper;

    WorkingOnNeo4JEntity mapToNeo4JEntity(WorkingOn workingOn, EmployeeNeo4JEntity employeeNeo4JEntity, ProjectNeo4JEntity projectNeo4JEntity) {
        return new WorkingOnNeo4JEntity(
                workingOn.getWorkingOnId(),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload(),
                employeeNeo4JEntity,
                projectNeo4JEntity);
    }

    WorkingOn mapToDomainEntity(WorkingOnNeo4JEntity workingOnNeo4JEntity) {
        return WorkingOn.withId(
                workingOnNeo4JEntity.getWorkingOnId(),
                workingOnNeo4JEntity.getSince(),
                workingOnNeo4JEntity.getUntil(),
                workingOnNeo4JEntity.getWorkload(),
                employeePersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getEmployee()),
                projectPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getProject()));
    }
}
