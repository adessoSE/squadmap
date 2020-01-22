package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.adapter.employee.GetEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.service.employee.GetEmployeeService;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @Autowired
    private GetEmployeeService service;
    @MockBean
    private GetEmployeeAdapter getEmployeeAdapter;
    @MockBean
    private EmployeeToResponseMapper employeeMapper;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = new Employee();
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse();
        when(getEmployeeAdapter.getEmployee(employeeId)).thenReturn(employee);
        when(employeeMapper.map(employee)).thenReturn(getEmployeeResponse);

        //when
        GetEmployeeResponse response = service.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeeAdapter, times(1)).getEmployee(employeeId);
        verify(employeeMapper, times(1)).map(employee);
        verifyNoMoreInteractions(getEmployeeAdapter);
        verifyNoMoreInteractions(employeeMapper);
    }
}
