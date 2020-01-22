package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.CreateEmployeeService;
import de.adesso.squadmap.utility.CreateCommandToEmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateEmployeeServiceTest {

    @Autowired
    private CreateEmployeeService service;
    @MockBean
    private CreateEmployeePort createEmployeePort;
    @MockBean
    private CreateCommandToEmployeeMapper employeeMapper;

    @Test
    void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();
        when(employeeMapper.map(createEmployeeCommand)).thenReturn(employee);
        when(createEmployeePort.createEmployee(employee)).thenReturn(employeeId);

        //when
        long found = service.createEmployee(createEmployeeCommand);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeMapper, times(1)).map(createEmployeeCommand);
        verify(createEmployeePort, times(1)).createEmployee(employee);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(createEmployeePort);
    }
}
