package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.CreateEmployeeService;
import de.adesso.squadmap.utility.CreateCommandToEmployeeMapper;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import de.adesso.squadmap.utility.Mapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class CreateEmployeeServiceTest {

    @Autowired
    private CreateEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private CreateCommandToEmployeeMapper employeeMapper;

    @Test
    public void checkIfCreateEmployeeCreatesAnEmployee() {
        //given
        long employeeId = 1;
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(employeeId);
        CreateEmployeeCommand testCommand = new CreateEmployeeCommand();
        Mockito.when(employeeMapper.map(testCommand)).thenReturn(testEmployee);
        Mockito.when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        //when
        long found = service.createEmployee(testCommand);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeRepository, times(1)).save(testEmployee);
        verify(employeeMapper, times(1)).map(testCommand);
    }
}
