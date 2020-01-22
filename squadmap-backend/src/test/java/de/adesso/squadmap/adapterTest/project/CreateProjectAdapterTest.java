package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.CreateProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateProjectAdapterTest {

    @Autowired
    private CreateProjectAdapter createProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfCreateProjectCreatesTheProject() {
        //given
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("t");
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectRepository.save(project)).thenReturn(project);

        //when
        long found = createProjectAdapter.createProject(project);

        //then
        assertThat(found).isEqualTo(project.getProjectId());
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectRepository, times(1)).save(project);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfCreateProjectThrowsProjectAlreadyExistsException() {
        //given
        Project project = new Project();
        project.setTitle("t");
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> createProjectAdapter.createProject(project));

        //then
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
    }
}
