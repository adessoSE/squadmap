package de.adesso.squadmap.adapterTest.employee;

import de.adesso.squadmap.adapter.employee.ListEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListEmployeeAdapterTest {

    @Autowired
    private ListEmployeeAdapter listEmployeeAdapter;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        Iterable projects = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(projects);

        //when
        List found = listEmployeeAdapter.listEmployees();

        //then
        assertThat(found).isEqualTo(projects);
        verify(employeeRepository, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepository);
    }
}
