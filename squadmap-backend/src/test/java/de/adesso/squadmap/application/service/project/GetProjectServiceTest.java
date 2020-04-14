package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GetProjectServiceTest {

    @Mock
    private GetProjectPort getProjectPort;
    @InjectMocks
    private GetProjectService getProjectService;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete().projectId(projectId).build();
        when(getProjectPort.getProject(projectId)).thenReturn(project);

        //when
        Project response = getProjectService.getProject(projectId);

        //then
        assertThat(response).isEqualTo(project);
        verify(getProjectPort, times(1)).getProject(projectId);
        verifyNoMoreInteractions(getProjectPort);
    }
}
