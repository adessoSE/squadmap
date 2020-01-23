package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateEmployeeServiceTest {

    @Autowired
    private UpdateEmployeeService service;
    @MockBean
    private UpdateEmployeePort updateEmployeePort;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(
                "", "", null, "", "", true, "");
        Employee employee = updateEmployeeCommand.toEmployee(employeeId);
        doNothing().when(updateEmployeePort).updateEmployee(employee);

        //when
        service.updateEmployee(updateEmployeeCommand, employeeId);

        //then
        assertThat(employee.getEmployeeId()).isEqualTo(employeeId);
        verify(updateEmployeePort, times(1)).updateEmployee(employee);
        verifyNoMoreInteractions(updateEmployeePort);
    }
}
