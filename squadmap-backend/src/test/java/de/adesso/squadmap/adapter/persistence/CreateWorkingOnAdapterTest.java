package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private WorkingOnPersistenceMapper workingOnPersistenceMapper;
    @Autowired
    private CreateWorkingOnAdapter createWorkingOnAdapter;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long workingOnId = workingOn.getWorkingOnId();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectNeo4JEntity));
        when(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn, employeeNeo4JEntity, projectNeo4JEntity)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity)).thenReturn(workingOnNeo4JEntity);

        //when
        long found = createWorkingOnAdapter.createWorkingOn(workingOn);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(workingOnPersistenceMapper, times(1)).mapToNeo4JEntity(workingOn, employeeNeo4JEntity, projectNeo4JEntity);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity);
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
        long projectId = workingOn.getProject().getProjectId();
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoInteractions(workingOnPersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfCreateWorkingOnThrowsProjectNotFoundException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoInteractions(workingOnPersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfCreateWorkingOnThrowsWorkingOnAlreadyExistsException() {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        long employeeId = workingOn.getEmployee().getEmployeeId();
        long projectId = workingOn.getProject().getProjectId();
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(true);

        //when
        assertThrows(WorkingOnAlreadyExistsException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verifyNoInteractions(workingOnPersistenceMapper);
        verifyNoInteractions(employeeRepository);
        verifyNoInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
