package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class WorkingOnPersistenceMapperTest {

    @MockBean
    private EmployeePersistenceMapper employeePersistenceMapper;
    @MockBean
    private ProjectPersistenceMapper projectPersistenceMapper;
    @Autowired
    private WorkingOnPersistenceMapper workingOnPersistenceMapper;

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntity() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();

        when(employeePersistenceMapper.mapToNeo4JEntity(workingOn.getEmployee())).thenReturn(employeeNeo4JEntity);
        when(projectPersistenceMapper.mapToNeo4JEntity(workingOn.getProject())).thenReturn(projectNeo4JEntity);

        //when
        WorkingOnNeo4JEntity workingOnNeo4JEntity = workingOnPersistenceMapper.mapToNeo4JEntity(workingOn);

        //then
        assertThat(workingOnNeo4JEntity.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnNeo4JEntity.getSince()).isEqualTo(workingOn.getSince());
        assertThat(workingOnNeo4JEntity.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(workingOnNeo4JEntity.getWorkload()).isEqualTo(workingOn.getWorkload());
        assertThat(workingOnNeo4JEntity.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnNeo4JEntity.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnNeo4JEntity.getEmployee()).isEqualTo(employeeNeo4JEntity);
        assertThat(workingOnNeo4JEntity.getProject()).isEqualTo(projectNeo4JEntity);
    }

    @Test
    void checkIfMapToDomainEntityMapsToValidEntityFromNeo4JEntity() {
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        Project project = ProjectMother.complete().build();
        when(employeePersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getEmployee())).thenReturn(employee);
        when(projectPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity.getProject())).thenReturn(project);

        //when
        WorkingOn workingOn = workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity);

        //then
        assertThat(workingOn.getWorkingOnId()).isEqualTo(workingOnNeo4JEntity.getWorkingOnId());
        assertThat(workingOn.getSince()).isEqualTo(workingOnNeo4JEntity.getSince());
        assertThat(workingOn.getUntil()).isEqualTo(workingOnNeo4JEntity.getUntil());
        assertThat(workingOn.getWorkload()).isEqualTo(workingOnNeo4JEntity.getWorkload());
        assertThat(workingOn.getWorkingOnId()).isEqualTo(workingOnNeo4JEntity.getWorkingOnId());
        assertThat(workingOn.getWorkingOnId()).isEqualTo(workingOnNeo4JEntity.getWorkingOnId());
        assertThat(workingOn.getEmployee()).isEqualTo(employee);
        assertThat(workingOn.getProject()).isEqualTo(project);
    }
}
