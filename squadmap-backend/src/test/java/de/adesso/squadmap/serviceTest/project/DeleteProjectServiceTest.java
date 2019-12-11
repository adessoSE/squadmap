package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.DeleteProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class DeleteProjectServiceTest {

    @Autowired
    private DeleteProjectService service;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfDeleteProjectDeletesTheProject() {
        //given
        long projectId = 1;
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.doNothing().when(projectRepository).deleteById(projectId);

        //when
        service.deleteProject(projectId);

        //then
        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void checkIfDeleteProjectThrowsExceptionWhenProjectNotFound() {
        //given
        long projectId = 1;
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(false);

        //then
        assertThrows(ProjectNotFoundException.class, () ->
                service.deleteProject(projectId));
    }
}
