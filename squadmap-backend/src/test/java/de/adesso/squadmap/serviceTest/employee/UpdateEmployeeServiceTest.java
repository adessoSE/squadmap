package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.UpdateEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateEmployeeServiceTest {

    @Autowired
    private UpdateEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        UpdateEmployeeCommand command = new UpdateEmployeeCommand("", "", LocalDate.now(), "", "", true, "");
        employee.setEmail(command.getEmail());
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(command.getEmail())).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        service.updateEmployee(command, employeeId);

        //then
        assertThat(employee.getFirstName()).isEqualTo(command.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(command.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(command.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(command.getEmail());
        assertThat(employee.getPhone()).isEqualTo(command.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(command.isExternal());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).existsByEmail(command.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesWithEmailChanged() {
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmail("");
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        command.setEmail("notEqual");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(command.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        service.updateEmployee(command, employeeId);

        //then
        assertThat(employee.getFirstName()).isEqualTo(command.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(command.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(command.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(command.getEmail());
        assertThat(employee.getPhone()).isEqualTo(command.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(command.isExternal());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).existsByEmail(command.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeThrowsExceptionWhenNotFound() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //then
        assertThrows(EmployeeNotFoundException.class, () ->
                service.updateEmployee(command, employeeId));
    }

    @Test
    void checkIfUpdateEmployeeThrowsExceptionWhenEmailAlreadyExists() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        command.setEmail("e@m.de");
        Employee employee = new Employee();
        employee.setEmail("");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(command.getEmail())).thenReturn(true);

        //then
        assertThrows(EmployeeAlreadyExistsException.class, () ->
                service.updateEmployee(command, employeeId));
    }
}
