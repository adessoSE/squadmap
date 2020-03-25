package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetProjectService.class)
@ActiveProfiles("test")
class GetProjectServiceTest {

    @MockBean
    private GetProjectPort getProjectPort;
    @MockBean
    private ListWorkingOnPort listWorkingOnPort;
    @MockBean
    private ResponseMapper<Project, GetProjectResponse> projectResponseMapper;
    @Autowired
    private GetProjectService getProjectService;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete().projectId(projectId).build();
        List<WorkingOn> projectsRelations = new ArrayList<>();
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(listWorkingOnPort.listWorkingOnByProjectId(projectId)).thenReturn(projectsRelations);
        when(projectResponseMapper.mapToResponseEntity(project, projectsRelations)).thenReturn(getProjectResponse);
        when(getProjectPort.getProject(projectId)).thenReturn(project);

        //when
        GetProjectResponse response = getProjectService.getProject(projectId);

        //then
        assertThat(response).isEqualTo(getProjectResponse);
        verify(listWorkingOnPort, times(1)).listWorkingOnByProjectId(projectId);
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project, projectsRelations);
        verify(getProjectPort, times(1)).getProject(projectId);
        verifyNoMoreInteractions(listWorkingOnPort);
        verifyNoMoreInteractions(projectResponseMapper);
        verifyNoMoreInteractions(getProjectPort);
    }
}
