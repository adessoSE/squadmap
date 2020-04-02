package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetEmployeeService.class)
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @MockBean
    private GetEmployeePort getEmployeePort;
    @Autowired
    private GetEmployeeService getEmployeeService;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .build();
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);

        //when
        Employee response = getEmployeeService.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(employee);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verifyNoMoreInteractions(getEmployeePort);
    }
}
