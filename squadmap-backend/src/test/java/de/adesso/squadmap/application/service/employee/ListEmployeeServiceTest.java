package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListEmployeeServiceTest {

    @Mock
    private ListEmployeePort listEmployeePort;
    @InjectMocks
    private ListEmployeeService listEmployeeService;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = EmployeeMother.complete().employeeId(1L).build();
        Employee employee2 = EmployeeMother.complete().employeeId(2L).build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(listEmployeePort.listEmployees()).thenReturn(employees);

        //when
        List<Employee> responses = listEmployeeService.listEmployees();

        //then
        assertThat(responses).isEqualTo(employees);
        verify(listEmployeePort, times(1)).listEmployees();
        verifyNoMoreInteractions(listEmployeePort);
    }
}
