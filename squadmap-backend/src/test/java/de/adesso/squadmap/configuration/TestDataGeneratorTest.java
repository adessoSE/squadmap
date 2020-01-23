package de.adesso.squadmap.configuration;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestDataGeneratorTest {

    @Mock
    CreateEmployeePort createEmployeePort;
    @Mock
    CreateProjectPort createProjectPort;
    @Mock
    CreateWorkingOnPort createWorkingOnPort;
    @Mock
    ListEmployeePort listEmployeePort;
    @Mock
    ListProjectPort listProjectPort;
    @InjectMocks
    TestDataGenerator testDataGenerator;


    @Test
    void checkIfRunGeneratesTestData() {
        //given
        when(listEmployeePort.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectPort.listProjects()).thenReturn(new ArrayList<>());

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeePort, times(1)).listEmployees();
        verify(listProjectPort, times(1)).listProjects();
        verify(createEmployeePort, times(10)).createEmployee(any(Employee.class));
        verify(createProjectPort, times(4)).createProject(any(Project.class));
        verify(createWorkingOnPort, times(10)).createWorkingOn(any(WorkingOn.class));
        verifyNoMoreInteractions(listEmployeePort);
        verifyNoMoreInteractions(listProjectPort);
        verifyNoMoreInteractions(createEmployeePort);
        verifyNoMoreInteractions(createProjectPort);
        verifyNoMoreInteractions(createWorkingOnPort);
    }

    @Test
    void checkIfRunGeneratesNothingWhenEmployeeOnRepositoryContainsData() {
        //given
        Employee employee = Employee.withoutId(
                "", "", null, "", "", true, "");
        when(listEmployeePort.listEmployees()).thenReturn(Collections.singletonList(employee));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeePort, times(1)).listEmployees();
        verifyNoMoreInteractions(listProjectPort);
        verifyNoInteractions(createEmployeePort);
        verifyNoInteractions(createProjectPort);
        verifyNoInteractions(createWorkingOnPort);
    }

    @Test
    void checkIfRunGeneratesNothingWhenProjectOnRepositoryContainsData() {
        //given
        Project project = Project.withoutId("", "", null, null, true, null);
        when(listEmployeePort.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectPort.listProjects()).thenReturn(Collections.singletonList(project));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeePort, times(1)).listEmployees();
        verify(listProjectPort, times(1)).listProjects();
        verifyNoMoreInteractions(listEmployeePort);
        verifyNoMoreInteractions(listProjectPort);
        verifyNoInteractions(createEmployeePort);
        verifyNoInteractions(createProjectPort);
        verifyNoInteractions(createWorkingOnPort);
    }
}
