package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GetEmployeeAdapterTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    @InjectMocks
    private GetEmployeeAdapter getEmployeeAdapter;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete().
                employeeId(employeeId).
                build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeePersistenceMapper.mapToDomainEntity(employeeNeo4JEntity)).thenReturn(employee);

        //when
        Employee found = getEmployeeAdapter.getEmployee(employeeId);

        //then
        assertThat(found).isEqualTo(employee);
        verify(employeeRepository, times(1)).findById(employeeId, 0);
        verify(employeePersistenceMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeePersistenceMapper);
    }

    @Test
    void checkIfGetEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> getEmployeeAdapter.getEmployee(employeeId));

        //then
        verify(employeeRepository, times(1)).findById(employeeId, 0);
        verifyNoMoreInteractions(employeeRepository);
    }
}
