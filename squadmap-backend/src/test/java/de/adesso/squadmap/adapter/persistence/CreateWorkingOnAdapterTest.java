package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateWorkingOnAdapterTest {

    @Mock
    private WorkingOnRepository workingOnRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> workingOnPersistenceMapper;
    @InjectMocks
    private CreateWorkingOnAdapter createWorkingOnAdapter;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long workingOnId = workingOn.getWorkingOnId();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity, 0)).thenReturn(workingOnNeo4JEntity);

        //when
        long found = createWorkingOnAdapter.createWorkingOn(workingOn);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(workingOnPersistenceMapper, times(1)).mapToNeo4JEntity(workingOn);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity, 0);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfCreateWorkingOnThrowsEmployeeNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //when
        assertThrows(EmployeeNotFoundException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfCreateWorkingOnThrowsProjectNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(false);

        //when
        assertThrows(ProjectNotFoundException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfCreateWorkingOnThrowsWorkingOnAlreadyExistsException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(true);

        //when
        assertThrows(WorkingOnAlreadyExistsException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }
}
