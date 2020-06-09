package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommandMother;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.EmployeeMother;
import de.adesso.squadmap.domain.mapper.EmployeeDomainMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UpdateEmployeeServiceTest {

    @Mock
    private UpdateEmployeePort updateEmployeePort;
    @Mock
    private EmployeeDomainMapper employeeDomainMapper;
    @InjectMocks
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
