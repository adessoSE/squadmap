package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
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

@SpringBootTest
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Autowired
    private ListEmployeeService service;
    @MockBean
    private ListEmployeePort listEmployeePort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = Employee.withId(
                1L, "", "", null, "", "", true, "");
        Employee employee2 = Employee.withId(
                2L, "", "", null, "", "", true, "");
        List<Employee> employees = Arrays.asList(employee1, employee2);
        List<WorkingOn> allRelations = new ArrayList<>();
        GetEmployeeResponse response1 = GetEmployeeResponse.getInstance(employee1, allRelations);
        GetEmployeeResponse response2 = GetEmployeeResponse.getInstance(employee2, allRelations);
        List<GetEmployeeResponse> getEmployeeResponses = Arrays.asList(response1, response2);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(listEmployeePort.listEmployees()).thenReturn(employees);

        //when
        List<GetEmployeeResponse> responses = service.listEmployees();

        //then
        assertThat(responses).isEqualTo(getEmployeeResponses);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(listEmployeePort, times(1)).listEmployees();
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(listEmployeePort);
    }
}
