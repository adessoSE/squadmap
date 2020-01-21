package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetProjectResponseWithoutEmployee;
import de.adesso.squadmap.port.driver.employee.get.GetWorkingOnResponseWithoutEmployee;
import de.adesso.squadmap.utility.EmployeeToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeToResponseMapperTest {

    @Autowired
    private EmployeeToResponseMapper mapper;

    @Test
    void checkIfMapMapsToValidEmployeeResponse() {
        //given
        Project project = new Project("t", "d", LocalDate.now(), LocalDate.now(), true, new ArrayList<>());
        project.setProjectId(1L);
        Employee employee = new Employee("f", "l", LocalDate.now(), "e", "0", true, "");
        employee.setEmployeeId(2L);
        WorkingOn workingOn = new WorkingOn(employee, project, LocalDate.now(), LocalDate.now(), 0);
        workingOn.setWorkingOnId(3L);
        employee.getProjects().add(workingOn);
        project.getEmployees().add(workingOn);

        //when
        GetEmployeeResponse employeeResponse = mapper.map(employee);
        GetWorkingOnResponseWithoutEmployee workingOnResponse = employeeResponse.getProjects().get(0);
        GetProjectResponseWithoutEmployee projectResponse = workingOnResponse.getProject();

        //then
        assertThat(employeeResponse.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeResponse.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeResponse.getBirthday()).isEqualTo(employee.getBirthday());
        assertThat(employeeResponse.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeResponse.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeResponse.getIsExternal()).isEqualTo(employee.getIsExternal());
        assertThat(employee.getProjects().size()).isEqualTo(1);

        assertThat(workingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnResponse.getSince()).isEqualTo(workingOn.getSince());
        assertThat(workingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(workingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());

        assertThat(projectResponse.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(projectResponse.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectResponse.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectResponse.getSince()).isEqualTo(project.getSince());
        assertThat(projectResponse.getUntil()).isEqualTo(project.getUntil());
        assertThat(projectResponse.getIsExternal()).isEqualTo(project.getIsExternal());
        assertThat(projectResponse.getSites()).isEqualTo(project.getSites());
    }
}
