package de.adesso.squadmap.adapterTest.employee;

import de.adesso.squadmap.adapter.employee.CreateEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.repository.EmployeeRepository;
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

    @Autowired
    private CreateEmployeeAdapter createEmployeeAdapter;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() {
        //given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmail("e");
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        long found = createEmployeeAdapter.createEmployee(employee);

        //then
        assertThat(found).isEqualTo(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfCreateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmail("e");
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> createEmployeeAdapter.createEmployee(employee));
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }
}
