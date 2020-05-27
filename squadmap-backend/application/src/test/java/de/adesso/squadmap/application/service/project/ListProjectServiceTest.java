package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.ProjectMother;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.WorkingOnMother;
import de.adesso.squadmap.domain.mapper.ProjectResponseMapper;
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
    private ProjectResponseMapper projectResponseMapper;
    @InjectMocks
    private ListProjectService listProjectService;

    @Test
    void checkIfListProjectsListsAllProjects() {
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
