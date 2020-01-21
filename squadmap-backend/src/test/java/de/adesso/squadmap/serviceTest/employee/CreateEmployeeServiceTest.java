package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
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
        when(employeeRepository.existsByEmail(command.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        long found = service.createEmployee(command);

        //then
        assertThat(found).isEqualTo(employeeId);
        verify(employeeMapper, times(1)).map(command);
        verify(employeeRepository, times(1)).existsByEmail(command.getEmail());
        verify(employeeRepository, times(1)).save(employee);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeeMapper);
    }

    @Test
    void checkIfCreateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        CreateEmployeeCommand command = new CreateEmployeeCommand("", "", LocalDate.now().minusMonths(5), "e@m.de", "", false, "");
        when(employeeRepository.existsByEmail(command.getEmail())).thenReturn(true);

        //then
        assertThrows(EmployeeAlreadyExistsException.class, () ->
                service.createEmployee(command));
    }
}
