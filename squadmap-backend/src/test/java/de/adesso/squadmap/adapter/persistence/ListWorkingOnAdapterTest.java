package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private WorkingOnPersistenceMapper workingOnPersistenceMapper;
    @Autowired
    private ListWorkingOnAdapter listWorkingOnAdapter;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = WorkingOnMother.complete()
                .workingOnId(1L)
                .build();
        WorkingOn workingOn2 = WorkingOnMother.complete()
                .workingOnId(2L)
                .build();
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        WorkingOnNeo4JEntity workingOnNeo4JEntity1 = WorkingOnNeo4JEntityMother.complete()
                .workingOnId(1L)
                .build();
        WorkingOnNeo4JEntity workingOnNeo4JEntity2 = WorkingOnNeo4JEntityMother.complete()
                .workingOnId(2L)
                .build();
        Iterable<WorkingOnNeo4JEntity> workingOnNeo4JEntities = Arrays.asList(workingOnNeo4JEntity1, workingOnNeo4JEntity2);
        when(workingOnRepository.findAll()).thenReturn(workingOnNeo4JEntities);
        when(workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity1)).thenReturn(workingOn1);
        when(workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity2)).thenReturn(workingOn2);

        //when
        List<WorkingOn> found = listWorkingOnAdapter.listWorkingOn();

        //then
        assertThat(found).isEqualTo(workingOns);
        verify(workingOnRepository, times(1)).findAll();
        verify(workingOnPersistenceMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity1);
        verify(workingOnPersistenceMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity2);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfListWorkingOnByEmployeeListsEmployeesWorkingOn() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        List<WorkingOn> workingOns = Collections.singletonList(workingOn);
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        List<WorkingOnNeo4JEntity> workingOnNeo4JEntities = Collections.singletonList(workingOnNeo4JEntity);
        long employeeId = workingOn.getEmployee().getEmployeeId();
        when(workingOnRepository.findAllByEmployeeId(employeeId)).thenReturn(workingOnNeo4JEntities);
        when(workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity)).thenReturn(workingOn);

        //when
        List<WorkingOn> found = listWorkingOnAdapter.listWorkingOnByEmployeeId(employeeId);

        //then
        assertThat(found).isEqualTo(workingOns);
        verify(workingOnRepository, times(1)).findAllByEmployeeId(employeeId);
        verify(workingOnPersistenceMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfListWorkingOnByProjectListsProjectsWorkingOn() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        List<WorkingOn> workingOns = Collections.singletonList(workingOn);
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        List<WorkingOnNeo4JEntity> workingOnNeo4JEntities = Collections.singletonList(workingOnNeo4JEntity);
        long projectId = workingOn.getProject().getProjectId();
        when(workingOnRepository.findAllByProjectId(projectId)).thenReturn(workingOnNeo4JEntities);
        when(workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity)).thenReturn(workingOn);

        //when
        List<WorkingOn> found = listWorkingOnAdapter.listWorkingOnByProjectId(projectId);

        //then
        assertThat(found).isEqualTo(workingOns);
        verify(workingOnRepository, times(1)).findAllByProjectId(projectId);
        verify(workingOnPersistenceMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
    }
}
