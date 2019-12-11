package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.GetEmployeeService;
import de.adesso.squadmap.utility.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class GetEmployeeServiceTest {

    @Autowired
    private GetEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeMapper employeeMapper;

    @Test
    public void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee testEmployee = new Employee();
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse();
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(testEmployee));
        Mockito.when(employeeMapper.mapEmployeeToEmployeeResponse(testEmployee)).thenReturn(getEmployeeResponse);

        //when
        GetEmployeeResponse response = service.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeMapper, times(1)).mapEmployeeToEmployeeResponse(testEmployee);
    }

    @Test
    public void checkIfGetEmployeeThrowsExceptionWhenNotFound() {
        //given
        long employeeId = 1;
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //when
        assertThrows(EmployeeNotFoundException.class, () ->
                service.getEmployee(employeeId));
    }
}
