package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @Autowired
    private GetEmployeeService service;
    @MockBean
    private GetEmployeePort getEmployeePort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = Employee.withId(
                employeeId, "", "", null, "", "", true, "");
        List<WorkingOn> allRelations = new ArrayList<>();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponse.getInstance(employee, allRelations);
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);

        //when
        GetEmployeeResponse response = service.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verifyNoMoreInteractions(getEmployeePort);
    }
}
