package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WorkingOnPersistenceMapper implements PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> {

    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    private final PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;

    public WorkingOnNeo4JEntity mapToNeo4JEntity(WorkingOn workingOn) {
        return WorkingOnNeo4JEntity.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .employee(employeePersistenceMapper.mapToNeo4JEntity(workingOn.getEmployee()))
                .project(projectPersistenceMapper.mapToNeo4JEntity(workingOn.getProject()))
                .build();
    }

    public WorkingOn mapToDomainEntity(WorkingOnNeo4JEntity workingOnNeo4JEntity) {
        return WorkingOn.builder()
                .workingOnId(workingOnNeo4JEntity.getWorkingOnId())
                .since(workingOnNeo4JEntity.getSince())
                .until(workingOnNeo4JEntity.getUntil())
                .workload(workingOnNeo4JEntity.getWorkload())
                .employee(employeePersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getEmployee()))
                .project(projectPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getProject()))
                .build();
    }
}
