package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.adapter.employee.UpdateEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.service.employee.UpdateEmployeeService;
import de.adesso.squadmap.utility.UpdateCommandToEmployeeMapper;
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
    private UpdateEmployeeAdapter updateEmployeeAdapter;
    @MockBean
    private UpdateCommandToEmployeeMapper employeeMapper;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();
        when(employeeMapper.map(updateEmployeeCommand)).thenReturn(employee);
        doNothing().when(updateEmployeeAdapter).updateEmployee(employee);

        //when
        service.updateEmployee(updateEmployeeCommand, employeeId);

        //then
        assertThat(employee.getEmployeeId()).isEqualTo(employeeId);
        verify(employeeMapper, times(1)).map(updateEmployeeCommand);
        verify(updateEmployeeAdapter, times(1)).updateEmployee(employee);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(updateEmployeeAdapter);
    }
}
