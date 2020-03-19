package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetEmployeeService.class)
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @MockBean
    private GetEmployeePort getEmployeePort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;
    @MockBean
    private ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @Autowired
    private GetEmployeeService getEmployeeService;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .build();
        List<WorkingOn> employeesRelations = new ArrayList<>();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(employeeResponseMapper.toResponse(employee, employeesRelations)).thenReturn(getEmployeeResponse);
        when(listWorkingOnPort.listWorkingOnByEmployeeId(employeeId)).thenReturn(employeesRelations);

        //when
        GetEmployeeResponse response = getEmployeeService.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(employeeResponseMapper, times(1)).toResponse(employee, employeesRelations);
        verify(listWorkingOnPort, times(1)).listWorkingOnByEmployeeId(employeeId);
        verifyNoMoreInteractions(getEmployeePort);
        verifyNoMoreInteractions(employeeResponseMapper);
        verifyNoMoreInteractions(listWorkingOnPort);
    }
}
