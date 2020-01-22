package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.CreateWorkingOnAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateWorkingOnAdapterTest {

    @Autowired
    private CreateWorkingOnAdapter createWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 1;
        long workingOnId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        Project project = new Project();
        project.setProjectId(projectId);
        WorkingOn workingOn = new WorkingOn();
        workingOn.setWorkingOnId(workingOnId);
        workingOn.setEmployee(employee);
        workingOn.setProject(project);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(false);
        when(workingOnRepository.save(workingOn)).thenReturn(workingOn);

        //when
        long found = createWorkingOnAdapter.createWorkingOn(workingOn);

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verify(workingOnRepository, times(1)).save(workingOn);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfCreateWorkingOnThrowsWorkingOnAlreadyExistsException() {
        //given
        long employeeId = 1;
        long projectId = 1;
        long workingOnId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        Project project = new Project();
        project.setProjectId(projectId);
        WorkingOn workingOn = new WorkingOn();
        workingOn.setWorkingOnId(workingOnId);
        workingOn.setEmployee(employee);
        workingOn.setProject(project);
        when(workingOnRepository.existsByEmployeeAndProject(employeeId, projectId)).thenReturn(true);

        //when
        assertThrows(WorkingOnAlreadyExistsException.class, () -> createWorkingOnAdapter.createWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsByEmployeeAndProject(employeeId, projectId);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
