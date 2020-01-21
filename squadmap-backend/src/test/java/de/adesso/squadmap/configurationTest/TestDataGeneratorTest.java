package de.adesso.squadmap.configurationTest;

import de.adesso.squadmap.configuration.TestDataGenerator;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestDataGeneratorTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    WorkingOnRepository workingOnRepository;
    @InjectMocks
    TestDataGenerator testDataGenerator;


    @Test
    void checkIfRunGeneratesTestData() {
        //given
        when(employeeRepository.count()).thenReturn(0L);
        when(projectRepository.count()).thenReturn(0L);
        when(workingOnRepository.count()).thenReturn(0L);

        //when
        testDataGenerator.run();

        //then
        verify(employeeRepository).count();
        verify(projectRepository).count();
        verify(workingOnRepository).count();
        verify(employeeRepository).saveAll(anyList());
        verify(projectRepository).saveAll(anyList());
        verify(workingOnRepository).saveAll(anyList());
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfRunGeneratesNothingWhenEmployeeOnRepositoryContainsData() {
        //given
        when(employeeRepository.count()).thenReturn(1L);

        //when
        testDataGenerator.run();

        //then
        verify(employeeRepository).count();
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfRunGeneratesNothingWhenProjectOnRepositoryContainsData() {
        //given
        when(employeeRepository.count()).thenReturn(0L);
        when(projectRepository.count()).thenReturn(1L);

        //when
        testDataGenerator.run();

        //then
        verify(employeeRepository).count();
        verify(projectRepository).count();
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfRunGeneratesNothingWhenWorkingOnRepositoryContainsData() {
        //given
        when(employeeRepository.count()).thenReturn(0L);
        when(projectRepository.count()).thenReturn(0L);
        when(workingOnRepository.count()).thenReturn(1L);

        //when
        testDataGenerator.run();

        //then
        verify(employeeRepository).count();
        verify(projectRepository).count();
        verify(workingOnRepository).count();
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
