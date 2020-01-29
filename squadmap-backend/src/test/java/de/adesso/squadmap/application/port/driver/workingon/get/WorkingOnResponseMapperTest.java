package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.*;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class WorkingOnResponseMapperTest {

    @MockBean
    private ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @MockBean
    private ResponseMapper<Project, GetProjectResponse> projectResponseMapper;
    @Autowired
    private WorkingOnResponseMapper workingOnResponseMapper;

    @Test
    void checkIfMapToResponseMapsToResponse() {
        //given
        Employee employee = EmployeeMother.complete().build();
        Project project = ProjectMother.complete().build();
        WorkingOn workingOn = WorkingOnMother.complete()
                .employee(employee)
                .project(project)
                .build();
        GetEmployeeResponse employeeResponse = GetEmployeeResponseMother.complete().build();
        GetProjectResponse projectResponse = GetProjectResponseMother.complete().build();
        List<WorkingOn> workingOnList = Collections.singletonList(workingOn);
        when(employeeResponseMapper.toResponse(employee, workingOnList)).thenReturn(employeeResponse);
        when(projectResponseMapper.toResponse(project, workingOnList)).thenReturn(projectResponse);

        //when
        GetWorkingOnResponse getWorkingOnResponse = workingOnResponseMapper.toResponse(workingOn, workingOnList);

        //then
        assertThat(getWorkingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(getWorkingOnResponse.getSince()).isEqualTo(workingOn.getSince());
        assertThat(getWorkingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(getWorkingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());
        assertThat(getWorkingOnResponse.getEmployee()).isEqualTo(employeeResponse);
        assertThat(getWorkingOnResponse.getProject()).isEqualTo(projectResponse);
        verify(employeeResponseMapper, times(1)).toResponse(employee, workingOnList);
        verify(projectResponseMapper, times(1)).toResponse(project, workingOnList);
    }
}
