package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.DeleteProjectPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DeleteProjectServiceTest {

    @Autowired
    private DeleteProjectService service;
    @MockBean
    private DeleteProjectPort deleteProjectPort;

    @Test
    void checkIfDeleteProjectDeletesTheProject() {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectPort).deleteProject(projectId);

        //when
        service.deleteProject(projectId);

        //then
        verify(deleteProjectPort, times(1)).deleteProject(projectId);
        verifyNoMoreInteractions(deleteProjectPort);
    }
}
