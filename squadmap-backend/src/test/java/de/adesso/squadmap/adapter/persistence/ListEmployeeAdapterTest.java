package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ListEmployeeAdapterTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    @InjectMocks
    private ListEmployeeAdapter listEmployeeAdapter;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity1 = EmployeeNeo4JEntityMother
                .complete()
                .employeeId(1L)
                .build();
        EmployeeNeo4JEntity employeeNeo4JEntity2 = EmployeeNeo4JEntityMother
                .complete()
                .employeeId(2L)
                .build();
        Iterable<EmployeeNeo4JEntity> employeeNeo4JEntities = Arrays.asList(employeeNeo4JEntity1, employeeNeo4JEntity2);
        Employee employee1 = EmployeeMother.complete()
                .employeeId(1L)
                .build();
        Employee employee2 = EmployeeMother.complete()
                .employeeId(2L)
                .build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employeeNeo4JEntities);
        when(employeePersistenceMapper.mapToDomainEntity(employeeNeo4JEntity1)).thenReturn(employee1);
        when(employeePersistenceMapper.mapToDomainEntity(employeeNeo4JEntity2)).thenReturn(employee2);

        //when
        List<Employee> found = listEmployeeAdapter.listEmployees();

        //then
        assertThat(found).isEqualTo(employees);
        verify(employeeRepository, times(1)).findAll();
        verify(employeePersistenceMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity1);
        verify(employeePersistenceMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity2);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeePersistenceMapper);
    }
}
