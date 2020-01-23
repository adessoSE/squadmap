package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreateProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectMapper projectMapper;
    @Autowired
    private CreateProjectAdapter createProjectAdapter;

    @Test
    void checkIfCreateProjectCreatesTheProject() {
        //given
        Project project = Project.withId(
                1L, "t", "", null, null, true, null);
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                1L, "", "", null, null, true, new ArrayList<>());
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity)).thenReturn(projectNeo4JEntity);

        //when
        long found = createProjectAdapter.createProject(project);

        //then
        assertThat(found).isEqualTo(project.getProjectId());
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectRepository, times(1)).save(projectNeo4JEntity);
        verify(projectMapper, times(1)).mapToNeo4JEntity(project);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectMapper);
    }

    @Test
    void checkIfCreateProjectThrowsProjectAlreadyExistsException() {
        //given
        Project project = Project.withId(
                1L, "t", "", null, null, true, null);
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> createProjectAdapter.createProject(project));

        //then
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
    }
}
