package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.adapter.employee.ListEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.ListEmployeeService;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Autowired
    private ListEmployeeService service;
    @MockBean
    private ListEmployeeAdapter listEmployeeAdapter;
    @MockBean
    private EmployeeToResponseMapper employeeMapper;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        List employees = Arrays.asList(employee1, employee2);
        GetEmployeeResponse response1 = new GetEmployeeResponse();
        response1.setEmployeeId(1L);
        GetEmployeeResponse response2 = new GetEmployeeResponse();
        response2.setEmployeeId(2L);
        List getEmployeeResponses = Arrays.asList(response1, response2);
        when(listEmployeeAdapter.listEmployees()).thenReturn(employees);
        when(employeeMapper.map(employee1)).thenReturn(response1);
        when(employeeMapper.map(employee2)).thenReturn(response2);

        //when
        List responses = service.listEmployees();

        //then
        assertThat(responses).isEqualTo(getEmployeeResponses);
        verify(listEmployeeAdapter, times(1)).listEmployees();
        verify(employeeMapper, times(1)).map(employee1);
        verify(employeeMapper, times(1)).map(employee2);
        verifyNoMoreInteractions(listEmployeeAdapter);
        verifyNoMoreInteractions(employeeMapper);
    }
}
