package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DeleteProjectAdapter.class)
@ActiveProfiles("test")
public class DeleteProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @Autowired
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
