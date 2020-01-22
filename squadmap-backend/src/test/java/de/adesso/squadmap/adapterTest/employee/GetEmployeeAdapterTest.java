package de.adesso.squadmap.adapterTest.employee;

import de.adesso.squadmap.adapter.employee.GetEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.repository.EmployeeRepository;
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

    @Autowired
    private GetEmployeeAdapter getEmployeeAdapter;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        //when
        Employee found = getEmployeeAdapter.getEmployee(employeeId);

        //then
        assertThat(found).isEqualTo(employee);
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfGetEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> getEmployeeAdapter.getEmployee(employeeId));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }
}
