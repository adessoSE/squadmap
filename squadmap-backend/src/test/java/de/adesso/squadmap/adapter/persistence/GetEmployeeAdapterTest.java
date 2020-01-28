package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class GetEmployeeAdapterTest {

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeePersistenceMapper employeePersistenceMapper;
    @Autowired
    private GetEmployeeAdapter getEmployeePort;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete().
                employeeId(employeeId).
                build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeePersistenceMapper.mapToDomainEntity(employeeNeo4JEntity)).thenReturn(employee);

        //when
        Employee found = getEmployeePort.getEmployee(employeeId);

        //then
        assertThat(found).isEqualTo(employee);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeePersistenceMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeePersistenceMapper);
    }

    @Test
    void checkIfGetEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> getEmployeePort.getEmployee(employeeId));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }
}
