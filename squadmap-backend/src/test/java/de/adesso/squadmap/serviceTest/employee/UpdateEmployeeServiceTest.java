package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.UpdateEmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateEmployeeServiceTest {

    @Autowired
    private UpdateEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        UpdateEmployeeCommand command = new UpdateEmployeeCommand("", "", LocalDate.now(), "", "", true);
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        service.updateEmployee(command, employeeId);

        //then
        assertThat(employee.getFirstName()).isEqualTo(command.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(command.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(command.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(command.getEmail());
        assertThat(employee.getPhone()).isEqualTo(command.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(command.getIsExternal());
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void checkIfUpdateEmployeeThrowsExceptionWhenNotFound() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //when
        assertThrows(EmployeeNotFoundException.class, () ->
                service.updateEmployee(command, employeeId));
    }
}
