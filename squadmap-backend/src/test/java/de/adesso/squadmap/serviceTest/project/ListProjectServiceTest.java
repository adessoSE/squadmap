package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.adapter.project.ListProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.ListProjectService;
import de.adesso.squadmap.utility.ProjectToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ListProjectServiceTest {

    @Autowired
    private ListProjectService service;
    @MockBean
    private ListProjectAdapter listProjectAdapter;
    @MockBean
    private ProjectToResponseMapper projectMapper;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project1 = new Project();
        project1.setProjectId(1L);
        Project project2 = new Project();
        project2.setProjectId(2L);
        List projects = Arrays.asList(project1, project2);
        GetProjectResponse response1 = new GetProjectResponse();
        response1.setProjectId(1L);
        GetProjectResponse response2 = new GetProjectResponse();
        response2.setProjectId(2L);
        List getProjectResponses = Arrays.asList(response1, response2);
        when(listProjectAdapter.listProjects()).thenReturn(projects);
        when(projectMapper.map(project1)).thenReturn(response1);
        when(projectMapper.map(project2)).thenReturn(response2);

        //when
        List responses = service.listProjects();

        //then
        assertThat(responses).isEqualTo(getProjectResponses);
        verify(listProjectAdapter, times(1)).listProjects();
        verify(projectMapper, times(1)).map(project1);
        verify(projectMapper, times(1)).map(project2);
        verifyNoMoreInteractions(listProjectAdapter);
        verifyNoMoreInteractions(projectMapper);
    }
}
