package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.ResponseMapper;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GetEmployeeServiceTest {

    @Mock
    private GetEmployeePort getEmployeePort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @InjectMocks
    private GetEmployeeService getEmployeeService;

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete().employeeId(employeeId).build();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().employeeId(employeeId).build();
        List<WorkingOn> workingOns = Collections.emptyList();
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(listWorkingOnPort.listWorkingOnByEmployeeId(employeeId)).thenReturn(workingOns);
        when(employeeResponseMapper.mapToResponseEntity(employee, workingOns)).thenReturn(getEmployeeResponse);

        //when
        GetEmployeeResponse response = getEmployeeService.getEmployee(employeeId);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(listWorkingOnPort, times(1)).listWorkingOnByEmployeeId(employeeId);
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee, workingOns);;
        verifyNoMoreInteractions(getEmployeePort, listWorkingOnPort, employeeResponseMapper);
    }
}
