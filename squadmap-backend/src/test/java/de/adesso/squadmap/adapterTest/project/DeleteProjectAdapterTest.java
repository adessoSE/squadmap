package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.DeleteProjectAdapter;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteProjectAdapterTest {

    @Autowired
    private DeleteProjectAdapter deleteProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

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
