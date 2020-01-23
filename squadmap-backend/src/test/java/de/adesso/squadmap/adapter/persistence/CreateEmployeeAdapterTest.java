package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateEmployeeAdapterTest {


    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeMapper employeeMapper;
    @Autowired
    private CreateEmployeePort createEmployeePort;

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() {
        //given
        Employee employee = Employee.withId(
                1L, "", "", null, "e", "", true, "");
        EmployeeNeo4JEntity employeeNeo4JEntity = new EmployeeNeo4JEntity(
                1L, "", "", null, "", "", true, "");
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity)).thenReturn(employeeNeo4JEntity);

        //when
        long found = createEmployeePort.createEmployee(employee);

        //then
        assertThat(found).isEqualTo(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfCreateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        Employee employee = Employee.withId(
                1L, "", "", null, "e", "", true, "");
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> createEmployeePort.createEmployee(employee));
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }
}
