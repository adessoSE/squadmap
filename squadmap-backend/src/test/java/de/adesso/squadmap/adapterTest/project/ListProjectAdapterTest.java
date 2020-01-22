package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.ListProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListProjectAdapterTest {

    @Autowired
    private ListProjectAdapter listProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfListProjectReturnsAllProjects() {
        //given
        Project project1 = new Project();
        project1.setProjectId(1L);
        Project project2 = new Project();
        project2.setProjectId(2L);
        Iterable projects = Arrays.asList(project1, project2);
        when(projectRepository.findAll()).thenReturn(projects);

        //when
        List found = listProjectAdapter.listProjects();

        //then
        assertThat(found).isEqualTo(projects);
        verify(projectRepository, times(1)).findAll();
        verifyNoMoreInteractions(projectRepository);
    }
}
