package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.adapter.project.DeleteProjectAdapter;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.DeleteProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DeleteProjectServiceTest {

    @Autowired
    private DeleteProjectService service;
    @MockBean
    private DeleteProjectAdapter deleteProjectAdapter;

    @Test
    void checkIfDeleteProjectDeletesTheProject() {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectAdapter).deleteProject(projectId);

        //when
        service.deleteProject(projectId);

        //then
        verify(deleteProjectAdapter, times(1)).deleteProject(projectId);
        verifyNoMoreInteractions(deleteProjectAdapter);
    }
}
