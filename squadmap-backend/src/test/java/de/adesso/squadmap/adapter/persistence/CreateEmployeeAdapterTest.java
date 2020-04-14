package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateEmployeeAdapterTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    @InjectMocks
    private CreateEmployeeAdapter createEmployeeAdapter;

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() {
        //given
        Employee employee = EmployeeMother.complete().build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeePersistenceMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity)).thenReturn(employeeNeo4JEntity);

        //when
        long found = createEmployeeAdapter.createEmployee(employee);

        //then
        assertThat(found).isEqualTo(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeePersistenceMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeePersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfCreateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        Employee employee = EmployeeMother.complete().build();
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> createEmployeeAdapter.createEmployee(employee));
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }
}
