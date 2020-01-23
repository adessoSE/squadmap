package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntity() {
    }

    @Test
    void checkIfMapToDomainEntityMapsToValidEntityFromNeo4JEntity() {

    }

//    @Test
//    void checkIfMapToDomainEntityMapsToValidEntityFromEmployeeCommand() {
//        //given
//        CreateEmployeeCommand command = new CreateEmployeeCommand("f", "l", LocalDate.now(), "e", "0", true, "i");
//
//        //when
//        Employee employee = employeeMapper.mapToDomainEntity(command);
//
//        //then
//        assertThat(employee.getFirstName()).isEqualTo(command.getFirstName());
//        assertThat(employee.getLastName()).isEqualTo(command.getLastName());
//        assertThat(employee.getBirthday()).isEqualTo(command.getBirthday());
//        assertThat(employee.getEmail()).isEqualTo(command.getEmail());
//        assertThat(employee.getPhone()).isEqualTo(command.getPhone());
//        assertThat(employee.getIsExternal()).isEqualTo(command.isExternal());
//        assertThat(employee.getImage()).isEqualTo(command.getImage());
//    }

    @Test
    void checkIfMapToResponseMapsToValidResponse() {
        //        //given
//        Project project = new Project("t", "d", LocalDate.now(), LocalDate.now(), true, new ArrayList<>());
//        project.setProjectId(1L);
//        Employee employee = new Employee("f", "l", LocalDate.now(), "e", "0", true, "");
//        employee.setEmployeeId(2L);
//        WorkingOn workingOn = new WorkingOn(employee, project, LocalDate.now(), LocalDate.now(), 0);
//        workingOn.setWorkingOnId(3L);
//        employee.getProjects().add(workingOn);
//        project.getEmployees().add(workingOn);
//
//        //when
//        GetEmployeeResponse employeeResponse = mapper.map(employee);
//        GetWorkingOnResponseWithoutEmployee workingOnResponse = employeeResponse.getProjects().get(0);
//        GetProjectResponseWithoutEmployee projectResponse = workingOnResponse.getProject();
//
//        //then
//        assertThat(employeeResponse.getEmployeeId()).isEqualTo(employee.getEmployeeId());
//        assertThat(employeeResponse.getFirstName()).isEqualTo(employee.getFirstName());
//        assertThat(employeeResponse.getLastName()).isEqualTo(employee.getLastName());
//        assertThat(employeeResponse.getBirthday()).isEqualTo(employee.getBirthday());
//        assertThat(employeeResponse.getEmail()).isEqualTo(employee.getEmail());
//        assertThat(employeeResponse.getPhone()).isEqualTo(employee.getPhone());
//        assertThat(employeeResponse.getIsExternal()).isEqualTo(employee.getIsExternal());
//        assertThat(employeeResponse.getImage()).isEqualTo(employee.getImage());
//        assertThat(employee.getProjects().size()).isEqualTo(1);
//
//        assertThat(workingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
//        assertThat(workingOnResponse.getSince()).isEqualTo(workingOn.getSince());
//        assertThat(workingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
//        assertThat(workingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());
//
//        assertThat(projectResponse.getProjectId()).isEqualTo(project.getProjectId());
//        assertThat(projectResponse.getTitle()).isEqualTo(project.getTitle());
//        assertThat(projectResponse.getDescription()).isEqualTo(project.getDescription());
//        assertThat(projectResponse.getSince()).isEqualTo(project.getSince());
//        assertThat(projectResponse.getUntil()).isEqualTo(project.getUntil());
//        assertThat(projectResponse.getIsExternal()).isEqualTo(project.getIsExternal());
//        assertThat(projectResponse.getSites()).isEqualTo(project.getSites());
    }
}
