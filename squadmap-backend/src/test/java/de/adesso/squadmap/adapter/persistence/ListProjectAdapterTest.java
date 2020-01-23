package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectMapper projectMapper;
    @Autowired
    private ListProjectAdapter listProjectAdapter;

    @Test
    void checkIfListProjectReturnsAllProjects() {
        //given
        ProjectNeo4JEntity projectNeo4JEntity1 = new ProjectNeo4JEntity(
                1L, "", "", null, null, true, new ArrayList<>());
        ProjectNeo4JEntity projectNeo4JEntity2 = new ProjectNeo4JEntity(
                2L, "", "", null, null, true, new ArrayList<>());
        Iterable<ProjectNeo4JEntity> projectNeo4JEntities = Arrays.asList(projectNeo4JEntity1, projectNeo4JEntity2);
        Project project1 = Project.withId(
                1L, "", "", null, null, true, null);
        Project project2 = Project.withId(
                2L, "", "", null, null, true, null);
        Iterable<Project> projects = Arrays.asList(project1, project2);
        when(projectRepository.findAll()).thenReturn(projectNeo4JEntities);
        when(projectMapper.mapToDomainEntity(projectNeo4JEntity1)).thenReturn(project1);
        when(projectMapper.mapToDomainEntity(projectNeo4JEntity2)).thenReturn(project2);

        //when
        List<Project> found = listProjectAdapter.listProjects();

        //then
        assertThat(found).isEqualTo(projects);
        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(1)).mapToDomainEntity(projectNeo4JEntity1);
        verify(projectMapper, times(1)).mapToDomainEntity(projectNeo4JEntity2);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectMapper);
    }
}
