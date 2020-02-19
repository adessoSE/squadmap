package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ListProjectAdapter.class)
@ActiveProfiles("test")
public class ListProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectPersistenceMapper projectPersistenceMapper;
    @Autowired
    private ListProjectAdapter listProjectAdapter;

    @Test
    void checkIfListProjectReturnsAllProjects() {
        //given
        ProjectNeo4JEntity projectNeo4JEntity1 = ProjectNeo4JEntityMother
                .complete()
                .projectId(1L)
                .build();
        ProjectNeo4JEntity projectNeo4JEntity2 = ProjectNeo4JEntityMother
                .complete()
                .projectId(2L)
                .build();
        Iterable<ProjectNeo4JEntity> projectNeo4JEntities = Arrays.asList(projectNeo4JEntity1, projectNeo4JEntity2);
        Project project1 = ProjectMother.complete()
                .projectId(1L)
                .build();
        Project project2 = ProjectMother.complete()
                .projectId(2L)
                .build();
        Iterable<Project> projects = Arrays.asList(project1, project2);
        when(projectRepository.findAll()).thenReturn(projectNeo4JEntities);
        when(projectPersistenceMapper.mapToDomainEntity(projectNeo4JEntity1)).thenReturn(project1);
        when(projectPersistenceMapper.mapToDomainEntity(projectNeo4JEntity2)).thenReturn(project2);

        //when
        List<Project> found = listProjectAdapter.listProjects();

        //then
        assertThat(found).isEqualTo(projects);
        verify(projectRepository, times(1)).findAll();
        verify(projectPersistenceMapper, times(1)).mapToDomainEntity(projectNeo4JEntity1);
        verify(projectPersistenceMapper, times(1)).mapToDomainEntity(projectNeo4JEntity2);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectPersistenceMapper);
    }
}
