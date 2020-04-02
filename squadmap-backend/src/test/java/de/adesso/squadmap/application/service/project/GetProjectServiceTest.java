package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetProjectService.class)
@ActiveProfiles("test")
class GetProjectServiceTest {

    @MockBean
    private GetProjectPort getProjectPort;
    @Autowired
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
