package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
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

@SpringBootTest
@ActiveProfiles("test")
class ListProjectServiceTest {

    @Autowired
    private ListProjectService service;
    @MockBean
    private ListProjectPort listProjectPort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project1 = Project.withId(
                1L, "", "", null, null, true, null);
        Project project2 = Project.withId(
                2L, "", "", null, null, true, null);
        List<Project> projects = Arrays.asList(project1, project2);
        List<WorkingOn> allRelations = new ArrayList<>();
        GetProjectResponse response1 = GetProjectResponse.getInstance(project1, allRelations);
        GetProjectResponse response2 = GetProjectResponse.getInstance(project2, allRelations);
        List<GetProjectResponse> getProjectResponses = Arrays.asList(response1, response2);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(allRelations);
        when(listProjectPort.listProjects()).thenReturn(projects);

        //when
        List<GetProjectResponse> responses = service.listProjects();

        //then
        assertThat(responses).isEqualTo(getProjectResponses);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verify(listProjectPort, times(1)).listProjects();
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(listProjectPort);
    }
}
