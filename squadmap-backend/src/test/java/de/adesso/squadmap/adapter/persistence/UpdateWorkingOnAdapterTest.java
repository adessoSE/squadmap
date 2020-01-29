package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private WorkingOnPersistenceMapper workingOnPersistenceMapper;
    @Autowired
    private UpdateWorkingOnAdapter updateWorkingOnAdapter;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long workingOnId = workingOn.getWorkingOnId();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity, 0)).thenReturn(workingOnNeo4JEntity);

        //when
        updateWorkingOnAdapter.updateWorkingOn(workingOn);

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnPersistenceMapper, times(1)).mapToNeo4JEntity(workingOn);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity, 0);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsEmployeeNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //when
        assertThrows(EmployeeNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsProjectNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(false);

        //when
        assertThrows(ProjectNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsWorkingOnNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        long workingOnId = workingOn.getWorkingOnId();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }
}
