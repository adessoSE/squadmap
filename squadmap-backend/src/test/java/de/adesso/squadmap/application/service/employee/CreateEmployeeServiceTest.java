package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateEmployeeServiceTest {

    @MockBean
    private CreateEmployeePort createEmployeePort;
    @Autowired
    private CreateEmployeeService service;

    @Test
    void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand(
                "", "", null, "", "", true, "");
        Employee employee = createEmployeeCommand.toEmployee();
        when(createEmployeePort.createEmployee(employee)).thenReturn(employeeId);

        //when
        long found = service.createEmployee(createEmployeeCommand);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(createEmployeePort, times(1)).createEmployee(employee);
        verifyNoMoreInteractions(createEmployeePort);
    }
}
