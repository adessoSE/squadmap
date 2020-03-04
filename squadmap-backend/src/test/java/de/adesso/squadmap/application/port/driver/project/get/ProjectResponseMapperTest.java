package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProjectResponseMapper.class)
@ActiveProfiles("test")
public class ProjectResponseMapperTest {

    @Autowired
    private ProjectResponseMapper projectResponseMapper;

    @Test
    void checkIfMapToResponseMapsToResponse() {
        //given
        Employee employee = EmployeeMother.complete().build();
        Project project = ProjectMother.complete().build();
        WorkingOn workingOn = WorkingOnMother.complete()
                .employee(employee)
                .project(project)
                .build();
        List<WorkingOn> workingOnList = Collections.singletonList(workingOn);

        //when
        GetProjectResponse getProjectResponse = projectResponseMapper.toResponse(project, workingOnList);

        //then
        assertThat(getProjectResponse.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(getProjectResponse.getTitle()).isEqualTo(project.getTitle());
        assertThat(getProjectResponse.getDescription()).isEqualTo(project.getDescription());
        assertThat(getProjectResponse.getSince()).isEqualTo(project.getSince());
        assertThat(getProjectResponse.getUntil()).isEqualTo(project.getUntil());
        assertThat(getProjectResponse.getIsExternal()).isEqualTo(project.getIsExternal());
        assertThat(getProjectResponse.getSites()).isEqualTo(project.getSites());

        assertThat(getProjectResponse.getEmployees()).isNotEmpty();
        GetWorkingOnResponseWithoutProject workingOnResponse = getProjectResponse.getEmployees().get(0);

        assertThat(workingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(workingOnResponse.getSince()).isEqualTo(workingOn.getSince());
        assertThat(workingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(workingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());

        assertThat(workingOnResponse.getEmployee()).isNotNull();
        GetEmployeeResponseWithoutProject employeeResponse = workingOnResponse.getEmployee();

        assertThat(employeeResponse.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeResponse.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeResponse.getBirthday()).isEqualTo(employee.getBirthday());
        assertThat(employeeResponse.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeResponse.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeResponse.getIsExternal()).isEqualTo(employee.getIsExternal());
        assertThat(employeeResponse.getImage()).isEqualTo(employee.getImage());
    }
}
