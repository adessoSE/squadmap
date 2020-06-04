package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.port.driven.project.DeleteProjectPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DeleteProjectServiceTest {

    @Mock
    private DeleteProjectPort deleteProjectPort;
    @InjectMocks
    private DeleteProjectService deleteProjectService;

    @Test
    void checkIfDeleteProjectDeletesTheProject() {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectPort).deleteProject(projectId);

        //when
        deleteProjectService.deleteProject(projectId);

        //then
        verify(deleteProjectPort, times(1)).deleteProject(projectId);
        verifyNoMoreInteractions(deleteProjectPort);
    }
}
