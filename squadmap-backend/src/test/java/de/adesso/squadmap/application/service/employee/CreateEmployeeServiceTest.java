package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateEmployeeService.class)
@ActiveProfiles("test")
class CreateEmployeeServiceTest {

    @MockBean
    private CreateEmployeePort createEmployeePort;
    @MockBean
    private EmployeeDomainMapper employeeMapper;
    @Autowired
    private CreateEmployeeService service;

    @Test
    void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        CreateEmployeeCommand createEmployeeCommand = CreateEmployeeCommandMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        when(employeeMapper.mapToDomainEntity(createEmployeeCommand)).thenReturn(employee);
        when(createEmployeePort.createEmployee(employee)).thenReturn(employeeId);

        //when
        long found = service.createEmployee(createEmployeeCommand);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeMapper, times(1)).mapToDomainEntity(createEmployeeCommand);
        verify(createEmployeePort, times(1)).createEmployee(employee);
        verifyNoMoreInteractions(employeeMapper);
        verifyNoMoreInteractions(createEmployeePort);
    }
}
