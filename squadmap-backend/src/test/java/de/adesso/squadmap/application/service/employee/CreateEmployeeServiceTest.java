package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CreateEmployeeServiceTest {

    @Mock
    private CreateEmployeePort createEmployeePort;
    @Mock
    private EmployeeDomainMapper employeeDomainMapper;
    @InjectMocks
    private CreateEmployeeService createEmployeeService;

    @Test
    void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        CreateEmployeeCommand createEmployeeCommand = CreateEmployeeCommandMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        when(employeeDomainMapper.mapToDomainEntity(createEmployeeCommand)).thenReturn(employee);
        when(createEmployeePort.createEmployee(employee)).thenReturn(employeeId);

        //when
        long found = createEmployeeService.createEmployee(createEmployeeCommand);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeDomainMapper, times(1)).mapToDomainEntity(createEmployeeCommand);
        verify(createEmployeePort, times(1)).createEmployee(employee);
        verifyNoMoreInteractions(employeeDomainMapper);
        verifyNoMoreInteractions(createEmployeePort);
    }
}
