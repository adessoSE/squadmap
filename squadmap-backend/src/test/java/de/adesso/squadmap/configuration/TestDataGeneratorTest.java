package de.adesso.squadmap.configuration;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
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
    CreateEmployeeUseCase createEmployeePort;
    @Mock
    CreateProjectUseCase createProjectPort;
    @Mock
    CreateWorkingOnUseCase createWorkingOnPort;
    @Mock
    ListEmployeeUseCase listEmployeePort;
    @Mock
    ListProjectUseCase listProjectPort;
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
        verify(createEmployeePort, times(10)).createEmployee(any(CreateEmployeeCommand.class));
        verify(createProjectPort, times(4)).createProject(any(CreateProjectCommand.class));
        verify(createWorkingOnPort, times(10)).createWorkingOn(any(CreateWorkingOnCommand.class));
        verifyNoMoreInteractions(listEmployeePort);
        verifyNoMoreInteractions(listProjectPort);
        verifyNoMoreInteractions(createEmployeePort);
        verifyNoMoreInteractions(createProjectPort);
        verifyNoMoreInteractions(createWorkingOnPort);
    }

    @Test
    void checkIfRunGeneratesNothingWhenEmployeeOnRepositoryContainsData() {
        //given
        GetEmployeeResponse employeeResponse = GetEmployeeResponseMother.complete().build();
        when(listEmployeePort.listEmployees()).thenReturn(Collections.singletonList(employeeResponse));

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
        GetProjectResponse projectResponse = GetProjectResponseMother.complete().build();
        when(listEmployeePort.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectPort.listProjects()).thenReturn(Collections.singletonList(projectResponse));

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
