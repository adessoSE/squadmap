package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.CreateEmployeeService;
import de.adesso.squadmap.utility.CreateCommandToEmployeeMapper;
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

    @Autowired
    private CreateEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private CreateCommandToEmployeeMapper employeeMapper;

    @Test
    void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        CreateEmployeeCommand command = new CreateEmployeeCommand();
        when(employeeMapper.map(command)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        long found = service.createEmployee(command);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).map(command);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeeMapper);
    }
}
