package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListEmployeeAdapterTest {


    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeMapper employeeMapper;
    @Autowired
    private ListEmployeePort listEmployeePort;

    @Test
    void checkIfListEmployeesListsAllEmployees() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity1 = new EmployeeNeo4JEntity(
                1L, "", "", null, "", "", true, "");
        EmployeeNeo4JEntity employeeNeo4JEntity2 = new EmployeeNeo4JEntity(
                2L, "", "", null, "", "", true, "");
        Iterable<EmployeeNeo4JEntity> employeeNeo4JEntities = Arrays.asList(employeeNeo4JEntity1, employeeNeo4JEntity2);
        Employee employee1 = Employee.withId(
                1L, "", "", null, "", "", true, "");
        Employee employee2 = Employee.withId(
                2L, "", "", null, "", "", true, "");
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employeeNeo4JEntities);
        when(employeeMapper.mapToDomainEntity(employeeNeo4JEntity1)).thenReturn(employee1);
        when(employeeMapper.mapToDomainEntity(employeeNeo4JEntity2)).thenReturn(employee2);

        //when
        List<Employee> found = listEmployeePort.listEmployees();

        //then
        assertThat(found).isEqualTo(employees);
        verify(employeeRepository, times(1)).findAll();
        verify(employeeMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity1);
        verify(employeeMapper, times(1)).mapToDomainEntity(employeeNeo4JEntity2);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoMoreInteractions(employeeMapper);
    }
}
