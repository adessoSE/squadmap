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
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectNeo4JEntity));
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn, employeeNeo4JEntity, projectNeo4JEntity)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity)).thenReturn(workingOnNeo4JEntity);

        //when
        updateWorkingOnAdapter.updateWorkingOn(workingOn);

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnPersistenceMapper, times(1)).mapToNeo4JEntity(workingOn, employeeNeo4JEntity, projectNeo4JEntity);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity);
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
        when(workingOnRepository.existsById(workingOn.getWorkingOnId())).thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOn.getWorkingOnId());
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsProjectNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(workingOnRepository.existsById(workingOn.getWorkingOnId())).thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOn.getWorkingOnId());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsWorkingOnNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long workingOnId = workingOn.getWorkingOnId();
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoInteractions(workingOnPersistenceMapper);
    }
}
