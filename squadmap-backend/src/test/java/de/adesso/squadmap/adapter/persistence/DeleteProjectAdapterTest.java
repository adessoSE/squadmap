package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DeleteProjectAdapterTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private DeleteProjectAdapter deleteProjectAdapter;

    @Test
    void checkIfDeleteProjectDeletesTheProject() {
        //given
        long projectId = 1;
        when(projectRepository.existsById(projectId)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(projectId);

        //when
        deleteProjectAdapter.deleteProject(projectId);

        //then
        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfDeleteProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        when(projectRepository.existsById(projectId)).thenReturn(false);

        //when
        assertThrows(ProjectNotFoundException.class, () -> deleteProjectAdapter.deleteProject(projectId));

        //then
        verify(projectRepository, times(1)).existsById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }
}
