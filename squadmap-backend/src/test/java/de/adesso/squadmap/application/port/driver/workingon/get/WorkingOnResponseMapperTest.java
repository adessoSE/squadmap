package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.*;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class WorkingOnResponseMapperTest {

    @Mock
    private EntityResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @Mock
    private EntityResponseMapper<Project, GetProjectResponse> projectResponseMapper;

    private WorkingOnResponseMapper workingOnResponseMapper;

    @BeforeEach
    void init() {
        workingOnResponseMapper = new WorkingOnResponseMapper(employeeResponseMapper, projectResponseMapper);
    }

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
        when(employeeResponseMapper.mapToResponseEntity(employee, workingOnList)).thenReturn(employeeResponse);
        when(projectResponseMapper.mapToResponseEntity(project, workingOnList)).thenReturn(projectResponse);

        //when
        GetWorkingOnResponse getWorkingOnResponse = workingOnResponseMapper
                .mapToResponseEntity(workingOn, workingOnList, workingOnList);

        //then
        assertThat(getWorkingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
        assertThat(getWorkingOnResponse.getSince()).isEqualTo(workingOn.getSince());
        assertThat(getWorkingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
        assertThat(getWorkingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());
        assertThat(getWorkingOnResponse.getEmployee()).isEqualTo(employeeResponse);
        assertThat(getWorkingOnResponse.getProject()).isEqualTo(projectResponse);
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee, workingOnList);
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project, workingOnList);
    }
}
