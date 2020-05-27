package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.ProjectMother;
import de.adesso.squadmap.domain.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GetProjectAdapterTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private PersistenceMapper<Project, ProjectNeo4JEntity> projectPersistenceMapper;
    @InjectMocks
    private GetProjectAdapter getProjectAdapter;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectPersistenceMapper.mapToDomainEntity(projectNeo4JEntity)).thenReturn(project);

        //when
        Project found = getProjectAdapter.getProject(projectId);

        //then
        assertThat(found).isEqualTo(project);
        verify(projectRepository, times(1)).findById(projectId, 0);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfGetProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        when(projectRepository.findById(projectId, 0)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> getProjectAdapter.getProject(projectId));

        //then
        verify(projectRepository, times(1)).findById(projectId, 0);
        verifyNoMoreInteractions(projectRepository);
    }
}
