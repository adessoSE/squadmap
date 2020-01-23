package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateProjectServiceTest {

    @Autowired
    private UpdateProjectService service;
    @MockBean
    private UpdateProjectPort updateProjectPort;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand(
                "", "", null, null, true, null);
        Project project = updateProjectCommand.toProject(projectId);
        doNothing().when(updateProjectPort).updateProject(project);

        //when
        service.updateProject(updateProjectCommand, projectId);

        //then
        assertThat(project.getProjectId()).isEqualTo(projectId);
        verify(updateProjectPort, times(1)).updateProject(project);
        verifyNoMoreInteractions(updateProjectPort);
    }
}
