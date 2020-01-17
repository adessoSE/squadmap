package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.GetEmployeeService;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @Autowired
    private GetEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeToResponseMapper employeeMapper;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        GetEmployeeResponse response = new GetEmployeeResponse();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.map(employee)).thenReturn(response);

        //when
        GetEmployeeResponse found = service.getEmployee(employeeId);

        //then
        assertThat(found).isEqualTo(response);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeMapper, times(1)).map(employee);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeeMapper);
    }

    @Test
    void checkIfGetEmployeeThrowsExceptionWhenNotFound() {
        //given
        long employeeId = 1;
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () ->
                service.getEmployee(employeeId));
    }
}
