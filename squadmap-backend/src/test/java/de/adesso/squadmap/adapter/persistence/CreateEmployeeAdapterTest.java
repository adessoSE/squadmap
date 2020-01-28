package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateEmployeeAdapterTest {


    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeePersistenceMapper employeePersistenceMapper;
    @Autowired
    private CreateEmployeePort createEmployeePort;

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() {
        //given
        Employee employee = EmployeeMother.complete().build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeePersistenceMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity)).thenReturn(employeeNeo4JEntity);

        //when
        long found = createEmployeePort.createEmployee(employee);

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
        assertThrows(EmployeeAlreadyExistsException.class, () -> createEmployeePort.createEmployee(employee));
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }
}
