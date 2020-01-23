package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateProjectServiceTest {

    @Autowired
    private CreateProjectService service;
    @MockBean
    private CreateProjectPort createProjectPort;

    @Test
    void checkIfCreateProjectCreatesAProject() {
        //given
        long projectId = 1;
        CreateProjectCommand createProjectCommand = new CreateProjectCommand(
                "", "", null, null, true, null);
        Project project = createProjectCommand.toProject();
        when(createProjectPort.createProject(project)).thenReturn(projectId);

        //when
        long found = service.createProject(createProjectCommand);

        //then
        assertThat(found).isEqualTo(projectId);
        verify(createProjectPort, times(1)).createProject(project);
        verifyNoMoreInteractions(createProjectPort);
    }
}
