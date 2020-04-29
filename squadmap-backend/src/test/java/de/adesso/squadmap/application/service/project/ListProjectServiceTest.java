package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListProjectServiceTest {

    @Mock
    private ListProjectPort listProjectPort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private EntityResponseMapper<Project, GetProjectResponse> projectResponseMapper;
    @InjectMocks
    private ListProjectService listProjectService;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project1 = ProjectMother.complete().projectId(1L).build();
        Project project2 = ProjectMother.complete().projectId(2L).build();
        List<Project> projects = Arrays.asList(project1, project2);
        GetProjectResponse getProjectResponse1 = GetProjectResponseMother.complete().projectId(1L).build();
        GetProjectResponse getProjectResponse2 = GetProjectResponseMother.complete().projectId(2L).build();
        List<GetProjectResponse> getProjectResponses = Arrays.asList(getProjectResponse1, getProjectResponse2);
        List<WorkingOn> workingOns = Collections.emptyList();
        when(listProjectPort.listProjects()).thenReturn(projects);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);
        when(projectResponseMapper.mapToResponseEntity(project1, workingOns)).thenReturn(getProjectResponse1);
        when(projectResponseMapper.mapToResponseEntity(project2, workingOns)).thenReturn(getProjectResponse2);

        //when
        List<GetProjectResponse> responses = listProjectService.listProjects();

        //then
        assertThat(responses).isEqualTo(getProjectResponses);
        verify(listProjectPort, times(1)).listProjects();
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project1, workingOns);
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project2, workingOns);
        verifyNoMoreInteractions(listProjectPort, listWorkingOnPort, projectResponseMapper);
    }

    @Test
    void checkIfListProjectsFiltersWorkingOns() {
        //given
        Project project = ProjectMother.complete().projectId(1L).build();
        WorkingOn projectsWorkingOn = WorkingOnMother.complete().project(project).build();
        WorkingOn unwantedWorkingOn = WorkingOnMother.complete()
                .project(ProjectMother.complete().projectId(2L).build())
                .build();
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(listProjectPort.listProjects()).thenReturn(Collections.singletonList(project));
        when(listWorkingOnPort.listWorkingOn()).thenReturn(Arrays.asList(projectsWorkingOn, unwantedWorkingOn));
        when(projectResponseMapper.mapToResponseEntity(project, Collections.singletonList(projectsWorkingOn)))
                .thenReturn(getProjectResponse);

        //when
        List<GetProjectResponse> responses = listProjectService.listProjects();

        //then
        assertThat(responses).isEqualTo(Collections.singletonList(getProjectResponse));
        verify(listProjectPort, times(1)).listProjects();
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(projectResponseMapper, times(1))
                .mapToResponseEntity(project, Collections.singletonList(projectsWorkingOn));
        verifyNoMoreInteractions(listProjectPort, listWorkingOnPort, projectResponseMapper);
    }
}
