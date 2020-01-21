package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.project.get.GetEmployeeResponseWithoutProject;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetWorkingOnResponseWithoutProject;
import de.adesso.squadmap.utility.ProjectToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProjectToResponseMapperTest {

    @Autowired
    private ProjectToResponseMapper mapper;

    @Test
    void checkIfMapMapsToValidProjectResponse() {
        //given
        Project project = new Project("t", "d", LocalDate.now(), LocalDate.now(), true, new ArrayList<>());
        project.setProjectId(1L);
        Employee employee = new Employee("f", "l", LocalDate.now(), "e", "0", true, "");
        employee.setEmployeeId(2L);
        WorkingOn workingOn = new WorkingOn(employee, project, LocalDate.now(), LocalDate.now(), 0);
        workingOn.setWorkingOnId(3L);
        project.getEmployees().add(workingOn);
        employee.getProjects().add(workingOn);

        //when
        GetProjectResponse projectResponse = mapper.map(project);
        GetWorkingOnResponseWithoutProject workingOnResponse = projectResponse.getEmployees().get(0);
        GetEmployeeResponseWithoutProject employeeResponse = workingOnResponse.getEmployee();

        //then
        assertThat(projectResponse.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(projectResponse.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectResponse.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectResponse.getSince()).isEqualTo(project.getSince());
        assertThat(projectResponse.getUntil()).isEqualTo(project.getUntil());
        assertThat(projectResponse.getIsExternal()).isEqualTo(project.getIsExternal());
        assertThat(projectResponse.getEmployees().size()).isEqualTo(1);
        assertThat(projectResponse.getSites()).isEqualTo(project.getSites());

        assertThat(workingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnResponse.getSince()).isEqualTo(workingOn.getSince());
        assertThat(workingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(workingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());

        assertThat(employeeResponse.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeResponse.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeResponse.getBirthday()).isEqualTo(employee.getBirthday());
        assertThat(employeeResponse.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeResponse.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeResponse.getIsExternal()).isEqualTo(employee.getIsExternal());
    }
}
