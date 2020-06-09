package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.ProjectMother;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.mapper.ProjectResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GetProjectServiceTest {

    @Mock
    private GetProjectPort getProjectPort;
    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @Mock
    private ProjectResponseMapper projectResponseMapper;
    @InjectMocks
    private GetProjectService getProjectService;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete().projectId(projectId).build();
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().projectId(projectId).build();
        List<WorkingOn> workingOns = Collections.emptyList();
        when(getProjectPort.getProject(projectId)).thenReturn(project);
        when(listWorkingOnPort.listWorkingOnByProjectId(projectId)).thenReturn(workingOns);
        when(projectResponseMapper.mapToResponseEntity(project, workingOns)).thenReturn(getProjectResponse);

        //when
        GetProjectResponse response = getProjectService.getProject(projectId);

        //then
        assertThat(response).isEqualTo(getProjectResponse);
        verify(getProjectPort, times(1)).getProject(projectId);
        verify(listWorkingOnPort, times(1)).listWorkingOnByProjectId(projectId);
        verify(projectResponseMapper, times(1)).mapToResponseEntity(project, workingOns);
        verifyNoMoreInteractions(getProjectPort, listWorkingOnPort, projectResponseMapper);
    }
}
