package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.domain.*;
import de.adesso.squadmap.domain.mapper.EmployeeResponseMapper;
import de.adesso.squadmap.domain.mapper.ProjectResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class WorkingOnResponseMapperTest {

    @Mock
    private EmployeeResponseMapper employeeResponseMapper;
    @Mock
    private ProjectResponseMapper projectResponseMapper;

    private WorkingOnResponseMapperImplementation workingOnResponseMapper;

    @BeforeEach
    void init() {
        workingOnResponseMapper = new WorkingOnResponseMapperImplementation(employeeResponseMapper, projectResponseMapper);
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
        Mockito.when(employeeResponseMapper.mapToResponseEntity(employee, workingOnList)).thenReturn(employeeResponse);
        Mockito.when(projectResponseMapper.mapToResponseEntity(project, workingOnList)).thenReturn(projectResponse);

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
        Mockito.verify(employeeResponseMapper, Mockito.times(1)).mapToResponseEntity(employee, workingOnList);
        Mockito.verify(projectResponseMapper, Mockito.times(1)).mapToResponseEntity(project, workingOnList);
    }
}
