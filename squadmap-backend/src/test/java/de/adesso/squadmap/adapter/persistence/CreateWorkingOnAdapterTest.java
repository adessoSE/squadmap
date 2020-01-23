package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private WorkingOnMapper workingOnMapper;
    @Autowired
    private CreateWorkingOnAdapter createWorkingOnAdapter;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 1;
        long workingOnId = 1;
        Employee employee = Employee.withId(
                employeeId, "", "", null, "", "", true, "");
        Project project = Project.withId(
                projectId, "", "", null, null, true, null);
        WorkingOn workingOn = WorkingOn.withId(
                workingOnId, null, null, 0, employee, project);
        WorkingOnNeo4JEntity workingOnNeo4JEntity = new WorkingOnNeo4JEntity(
                workingOnId, null, null, 0, null, null);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(workingOnMapper.mapToNeo4JEntity(workingOn)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity)).thenReturn(workingOnNeo4JEntity);

        //when
        long found = createWorkingOnAdapter.createWorkingOn(workingOn);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(workingOnMapper, times(1)).mapToNeo4JEntity(workingOn);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity);
        verifyNoMoreInteractions(workingOnMapper);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfCreateWorkingOnThrowsWorkingOnAlreadyExistsException() {
        //given
        long employeeId = 1;
        long projectId = 1;
        long workingOnId = 1;
        Employee employee = Employee.withId(
                employeeId, "", "", null, "", "", true, "");
        Project project = Project.withId(
                projectId, "", "", null, null, true, null);
        WorkingOn workingOn = WorkingOn.withId(
                workingOnId, null, null, 0, employee, project);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(true);

        //when
        assertThrows(WorkingOnAlreadyExistsException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
