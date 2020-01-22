package de.adesso.squadmap.adapterTest.employee;

import de.adesso.squadmap.adapter.employee.UpdateEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateEmployeeAdapterTest {

    @Autowired
    private UpdateEmployeeAdapter updateEmployeeAdapter;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmail("e");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        updateEmployeeAdapter.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployeeWithEmailChanged() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmail("e");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        updateEmployeeAdapter.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> updateEmployeeAdapter.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmail("e");
        Employee existingEmployee = new Employee();
        existingEmployee.setEmail("f");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> updateEmployeeAdapter.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }
}
