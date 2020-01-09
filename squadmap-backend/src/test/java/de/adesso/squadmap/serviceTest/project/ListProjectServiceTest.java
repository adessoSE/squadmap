package de.adesso.squadmap.serviceTest.project;

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
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectToResponseMapper projectMapper;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project = new Project();
        List<Project> allProjects = Collections.singletonList(project);
        GetProjectResponse response = new GetProjectResponse();
        when(projectRepository.findAll()).thenReturn(allProjects);
        when(projectMapper.map(project)).thenReturn(response);

        //when
        List<GetProjectResponse> found = service.listProjects();

        //then
        assertThat(found).isEqualTo(Collections.singletonList(response));
        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(1)).map(project);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectMapper);
    }
}
