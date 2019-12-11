package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.ListEmployeeService;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Autowired
    private ListEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeToResponseMapper employeeMapper;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee = new Employee();
        GetEmployeeResponse response = new GetEmployeeResponse();
        List<Employee> employeeList = Collections.singletonList(employee);
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        Mockito.when(employeeMapper.map(employee)).thenReturn(response);

        //when
        List<GetEmployeeResponse> found = service.listEmployees();

        //then
        assertThat(found).isEqualTo(Collections.singletonList(response));
        verify(employeeRepository, times(1)).findAll();
        verify(employeeMapper, times(1)).map(employee);

    }
}
