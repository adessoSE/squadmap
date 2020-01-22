package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.adapter.project.GetProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.GetProjectService;
import de.adesso.squadmap.utility.ProjectToResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetProjectServiceTest {

    @Autowired
    private GetProjectService service;
    @MockBean
    private GetProjectAdapter getProjectAdapter;
    @MockBean
    private ProjectToResponseMapper projectMapper;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        GetProjectResponse getProjectResponse = new GetProjectResponse();
        when(getProjectAdapter.getProject(projectId)).thenReturn(project);
        when(projectMapper.map(project)).thenReturn(getProjectResponse);

        //when
        GetProjectResponse response = service.getProject(projectId);

        //then
        assertThat(response).isEqualTo(getProjectResponse);
        verify(getProjectAdapter, times(1)).getProject(projectId);
        verify(projectMapper, times(1)).map(project);
        verifyNoMoreInteractions(getProjectAdapter);
        verifyNoMoreInteractions(projectMapper);
    }
}
