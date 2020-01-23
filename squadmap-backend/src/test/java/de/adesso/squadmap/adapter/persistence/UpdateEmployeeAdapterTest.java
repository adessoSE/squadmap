package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateEmployeeAdapterTest {

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeMapper employeeMapper;
    @Autowired
    private UpdateEmployeePort updateEmployeePort;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = Employee.withId(
                employeeId, "", "", null, "e", "", true, "");
        EmployeeNeo4JEntity employeeNeo4JEntity = new EmployeeNeo4JEntity(
                1L, "", "", null, "e", "", true, "");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        when(employeeMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity)).thenReturn(employeeNeo4JEntity);

        //when
        updateEmployeePort.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployeeWithEmailChanged() {
        //given
        long employeeId = 1;
        Employee employee = Employee.withId(
                1L, "", "", null, "e", "", true, "");
        EmployeeNeo4JEntity employeeNeo4JEntity = new EmployeeNeo4JEntity(
                1L, "", "", null, "f", "", true, "");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity)).thenReturn(employeeNeo4JEntity);

        //when
        updateEmployeePort.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId());
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeeMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        Employee employee = Employee.withId(
                1L, "", "", null, "", "", true, "");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> updateEmployeePort.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(employeeMapper);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        long employeeId = 1;
        Employee employee = Employee.withId(
                1L, "", "", null, "e", "", true, "");
        EmployeeNeo4JEntity existingEmployee = new EmployeeNeo4JEntity(
                1L, "", "", null, "f", "", true, "");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> updateEmployeePort.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(employeeMapper);
    }
}
