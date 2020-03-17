package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WorkingOnPersistenceMapper implements PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> {

    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;

    public WorkingOnNeo4JEntity mapToNeo4JEntity(WorkingOn workingOn) {
        return new WorkingOnNeo4JEntity(
                workingOn.getWorkingOnId(),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload(),
                employeePersistenceMapper.mapToNeo4JEntity(workingOn.getEmployee()),
                projectPersistenceMapper.mapToNeo4JEntity(workingOn.getProject()));
    }

    public WorkingOn mapToDomainEntity(WorkingOnNeo4JEntity workingOnNeo4JEntity) {
        return new WorkingOn(
                workingOnNeo4JEntity.getWorkingOnId(),
                workingOnNeo4JEntity.getSince(),
                workingOnNeo4JEntity.getUntil(),
                workingOnNeo4JEntity.getWorkload(),
                employeePersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getEmployee()),
                projectPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getProject()));
    }
}
