package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ListProjectService.class)
@ActiveProfiles("test")
class ListProjectServiceTest {

    @MockBean
    private ListProjectPort listProjectPort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;
    @MockBean
    private ResponseMapper<Project, GetProjectResponse> projectResponseMapper;
    @Autowired
    private ListProjectService listProjectService;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project1 = ProjectMother.complete().projectId(1L).build();
        Project project2 = ProjectMother.complete().projectId(2L).build();
        List<Project> projects = Arrays.asList(project1, project2);
        List<WorkingOn> allRelations = new ArrayList<>();
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(projectResponseMapper.mapToResponseEntity(project1, allRelations)).thenReturn(getProjectResponse);
        when(projectResponseMapper.mapToResponseEntity(project2, allRelations)).thenReturn(getProjectResponse);
        when(listProjectPort.listProjects()).thenReturn(projects);

        //when
        List<GetProjectResponse> responses = listProjectService.listProjects();

        //then
        responses.forEach(response -> assertThat(response).isEqualTo(getProjectResponse));
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project1, allRelations);
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project2, allRelations);
        verify(listProjectPort, times(1)).listProjects();
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(projectResponseMapper);
        verifyNoMoreInteractions(listProjectPort);
    }
}
