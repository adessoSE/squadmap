package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Mock
    private ListEmployeePort listEmployeePort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private EntityResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @InjectMocks
    private ListEmployeeService listEmployeeService;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = EmployeeMother.complete().employeeId(1L).build();
        Employee employee2 = EmployeeMother.complete().employeeId(2L).build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        GetEmployeeResponse getEmployeeResponse1 = GetEmployeeResponseMother.complete().build();
        GetEmployeeResponse getEmployeeResponse2 = GetEmployeeResponseMother.complete().build();
        List<GetEmployeeResponse> getEmployeeResponses = Arrays.asList(getEmployeeResponse1, getEmployeeResponse2);
        List<WorkingOn> workingOns = Collections.emptyList();
        when(listEmployeePort.listEmployees()).thenReturn(employees);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);
        when(employeeResponseMapper.mapToResponseEntity(employee1, workingOns)).thenReturn(getEmployeeResponse1);
        when(employeeResponseMapper.mapToResponseEntity(employee2, workingOns)).thenReturn(getEmployeeResponse2);

        //when
        List<GetEmployeeResponse> responses = listEmployeeService.listEmployees();

        //then
        assertThat(responses).isEqualTo(getEmployeeResponses);
        verify(listEmployeePort, times(1)).listEmployees();
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee1, workingOns);
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee2, workingOns);
        verifyNoMoreInteractions(listEmployeePort, listWorkingOnPort, employeeResponseMapper);
    }

    @Test
    void checkIfListEmployeeFiltersWorkingOnList() {
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
        assertThat(responses).isEqualTo(Collections.singletonList(employeeResponse));
        verify(listEmployeePort, times(1)).listEmployees();
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(employeeResponseMapper, times(1))
                .mapToResponseEntity(employee, Collections.singletonList(employeesWorkingOn));
        verifyNoMoreInteractions(listEmployeePort, listWorkingOnPort, employeeResponseMapper);
    }
}
