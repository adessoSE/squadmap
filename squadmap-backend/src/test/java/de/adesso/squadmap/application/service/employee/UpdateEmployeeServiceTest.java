package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = UpdateEmployeeService.class)
@ActiveProfiles("test")
class UpdateEmployeeServiceTest {

    @MockBean
    private UpdateEmployeePort updateEmployeePort;
    @MockBean
    private EmployeeDomainMapper employeeDomainMapper;
    @Autowired
    private UpdateEmployeeService updateEmployeeService;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand updateEmployeeCommand = UpdateEmployeeCommandMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        when(employeeDomainMapper.mapToDomainEntity(updateEmployeeCommand, employeeId)).thenReturn(employee);
        doNothing().when(updateEmployeePort).updateEmployee(employee);

        //when
        updateEmployeeService.updateEmployee(updateEmployeeCommand, employeeId);

        //then
        verify(employeeDomainMapper, times(1)).mapToDomainEntity(updateEmployeeCommand, employeeId);
        verify(updateEmployeePort, times(1)).updateEmployee(employee);
        verifyNoMoreInteractions(employeeDomainMapper);
        verifyNoMoreInteractions(updateEmployeePort);
    }
}
