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
    CreateEmployeeUseCase createEmployeeUseCase;
    @Mock
    CreateProjectUseCase createProjectUseCase;
    @Mock
    CreateWorkingOnUseCase createWorkingOnUseCase;
    @Mock
    ListEmployeeUseCase listEmployeeUseCase;
    @Mock
    ListProjectUseCase listProjectUseCase;
    @InjectMocks
    TestDataGenerator testDataGenerator;


    @Test
    void checkIfRunGeneratesTestData() {
        //given
        when(listEmployeeUseCase.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectUseCase.listProjects()).thenReturn(new ArrayList<>());

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verify(listProjectUseCase, times(1)).listProjects();
        verify(createEmployeeUseCase, times(10)).createEmployee(any(CreateEmployeeCommand.class));
        verify(createProjectUseCase, times(4)).createProject(any(CreateProjectCommand.class));
        verify(createWorkingOnUseCase, times(10)).createWorkingOn(any(CreateWorkingOnCommand.class));
        verifyNoMoreInteractions(listEmployeeUseCase);
        verifyNoMoreInteractions(listProjectUseCase);
        verifyNoMoreInteractions(createEmployeeUseCase);
        verifyNoMoreInteractions(createProjectUseCase);
        verifyNoMoreInteractions(createWorkingOnUseCase);
    }

    @Test
    void checkIfRunGeneratesNothingWhenEmployeeOnRepositoryContainsData() {
        //given
        GetEmployeeResponse employeeResponse = GetEmployeeResponseMother.complete().build();
        when(listEmployeeUseCase.listEmployees()).thenReturn(Collections.singletonList(employeeResponse));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verifyNoMoreInteractions(listProjectUseCase);
        verifyNoInteractions(createEmployeeUseCase);
        verifyNoInteractions(createProjectUseCase);
        verifyNoInteractions(createWorkingOnUseCase);
    }

    @Test
    void checkIfRunGeneratesNothingWhenProjectOnRepositoryContainsData() {
        //given
        GetProjectResponse projectResponse = GetProjectResponseMother.complete().build();
        when(listEmployeeUseCase.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectUseCase.listProjects()).thenReturn(Collections.singletonList(projectResponse));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verify(listProjectUseCase, times(1)).listProjects();
        verifyNoMoreInteractions(listEmployeeUseCase);
        verifyNoMoreInteractions(listProjectUseCase);
        verifyNoInteractions(createEmployeeUseCase);
        verifyNoInteractions(createProjectUseCase);
        verifyNoInteractions(createWorkingOnUseCase);
    }
}
