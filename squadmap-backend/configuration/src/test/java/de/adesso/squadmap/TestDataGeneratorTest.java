package de.adesso.squadmap;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TestDataGeneratorTest {

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
        verifyNoMoreInteractions(listEmployeeUseCase, listProjectUseCase,
                createEmployeeUseCase, createProjectUseCase, createWorkingOnUseCase);
    }

    @Test
    void checkIfRunGeneratesNothingWhenEmployeeOnRepositoryContainsData() {
        //given
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        when(listEmployeeUseCase.listEmployees()).thenReturn(Collections.singletonList(getEmployeeResponse));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verifyNoMoreInteractions(listProjectUseCase,
                createEmployeeUseCase, createProjectUseCase, createWorkingOnUseCase);
    }

    @Test
    void checkIfRunGeneratesNothingWhenProjectOnRepositoryContainsData() {
        //given
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(listEmployeeUseCase.listEmployees()).thenReturn(new ArrayList<>());
        when(listProjectUseCase.listProjects()).thenReturn(Collections.singletonList(getProjectResponse));

        //when
        testDataGenerator.run();

        //then
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verify(listProjectUseCase, times(1)).listProjects();
        verifyNoMoreInteractions(listEmployeeUseCase, listProjectUseCase,
                createEmployeeUseCase, createProjectUseCase, createWorkingOnUseCase);
    }
}
