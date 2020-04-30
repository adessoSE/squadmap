package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UpdateProjectAdapterTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;
    @InjectMocks
    private UpdateProjectAdapter updateProjectAdapter;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .title("t")
                .build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete()
                .projectId(projectId)
                .title("t")
                .build();
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);
        when(projectPersistenceMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity, 0)).thenReturn(projectNeo4JEntity);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId, 0);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectPersistenceMapper, times(1)).mapToNeo4JEntity(project);
        verify(projectRepository, times(1)).save(projectNeo4JEntity, 0);
        verifyNoMoreInteractions(projectPersistenceMapper);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProjectWithTitleChanged() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .title("t")
                .build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete()
                .projectId(projectId)
                .title("f")
                .build();
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectPersistenceMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity, 0)).thenReturn(projectNeo4JEntity);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId, 0);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectPersistenceMapper, times(1)).mapToNeo4JEntity(project);
        verify(projectRepository, times(1)).save(projectNeo4JEntity, 0);
        verifyNoMoreInteractions(projectPersistenceMapper);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .build();
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId, 0);
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(projectPersistenceMapper);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectAlreadyExistsException() {
        //given
        long projectId = 0;
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .title("t")
                .build();
        ProjectNeo4JEntity existingProject = ProjectNeo4JEntityMother.complete()
                .projectId(projectId)
                .title("f")
                .build();
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.of(existingProject));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId, 0);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(projectPersistenceMapper);
    }
}
