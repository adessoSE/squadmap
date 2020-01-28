package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import org.junit.jupiter.api.Test;
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

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectPersistenceMapper projectPersistenceMapper;
    @Autowired
    private CreateProjectAdapter createProjectAdapter;

    @Test
    void checkIfCreateProjectCreatesTheProject() {
        //given
        Project project = ProjectMother.complete().build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectPersistenceMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity)).thenReturn(projectNeo4JEntity);

        //when
        long found = createProjectAdapter.createProject(project);

        //then
        assertThat(found).isEqualTo(project.getProjectId());
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectRepository, times(1)).save(projectNeo4JEntity);
        verify(projectPersistenceMapper, times(1)).mapToNeo4JEntity(project);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectPersistenceMapper);
    }

    @Test
    void checkIfCreateProjectThrowsProjectAlreadyExistsException() {
        //given
        Project project = ProjectMother.complete().build();
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> createProjectAdapter.createProject(project));

        //then
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
    }
}
