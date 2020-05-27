package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.EmployeeMother;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.WorkingOnMother;
import de.adesso.squadmap.domain.mapper.EmployeeResponseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Mock
    private ListEmployeePort listEmployeePort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private EmployeeResponseMapper employeeResponseMapper;
    @InjectMocks
    private ListEmployeeService listEmployeeService;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee = EmployeeMother.complete().employeeId(1L).build();
        WorkingOn employeesWorkingOn = WorkingOnMother.complete().employee(employee).build();
        WorkingOn unwantedWorkingOn = WorkingOnMother.complete()
                .employee(EmployeeMother.complete().employeeId(2L).build())
                .build();
        GetEmployeeResponse employeeResponse = GetEmployeeResponseMother.complete().build();
        when(listEmployeePort.listEmployees()).thenReturn(Collections.singletonList(employee));
        when(listWorkingOnPort.listWorkingOn()).thenReturn(Arrays.asList(employeesWorkingOn, unwantedWorkingOn));
        when(employeeResponseMapper.mapToResponseEntity(employee, Collections.singletonList(employeesWorkingOn)))
                .thenReturn(employeeResponse);

        //when
        List<GetEmployeeResponse> responses = listEmployeeService.listEmployees();

        //then
        Assertions.assertThat(responses).isEqualTo(Collections.singletonList(employeeResponse));
        verify(listEmployeePort, times(1)).listEmployees();
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(employeeResponseMapper, times(1))
                .mapToResponseEntity(employee, Collections.singletonList(employeesWorkingOn));
        verifyNoMoreInteractions(listEmployeePort, listWorkingOnPort, employeeResponseMapper);
    }
}
