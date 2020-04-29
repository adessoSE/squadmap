package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateProjectAdapterTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;
    @InjectMocks
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
