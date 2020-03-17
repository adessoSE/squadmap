package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ListEmployeeService.class)
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @MockBean
    private ListEmployeePort listEmployeePort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;
    @MockBean
    private ResponseMapper<Employee, GetEmployeeResponse> responseMapper;
    @Autowired
    private ListEmployeeService service;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = EmployeeMother.complete().employeeId(1L).build();
        Employee employee2 = EmployeeMother.complete().employeeId(2L).build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        List<WorkingOn> allRelations = new ArrayList<>();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(responseMapper.toResponse(employee1, allRelations)).thenReturn(getEmployeeResponse);
        when(responseMapper.toResponse(employee2, allRelations)).thenReturn(getEmployeeResponse);
        when(listEmployeePort.listEmployees()).thenReturn(employees);

        //when
        List<GetEmployeeResponse> responses = service.listEmployees();

        //then
        responses.forEach(response -> assertThat(response).isEqualTo(getEmployeeResponse));
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(responseMapper, times(1)).toResponse(employee1, allRelations);
        verify(responseMapper, times(1)).toResponse(employee2, allRelations);
        verify(listEmployeePort, times(1)).listEmployees();
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(responseMapper);
        verifyNoMoreInteractions(listEmployeePort);
    }
}
