package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListProjectServiceTest {

    @Mock
    private ListProjectPort listProjectPort;
    @InjectMocks
    private ListProjectService listProjectService;

    @Test
    void checkIfListProjectsListsAllProjects() {
        //given
        Project project1 = ProjectMother.complete().projectId(1L).build();
        Project project2 = ProjectMother.complete().projectId(2L).build();
        List<Project> projects = Arrays.asList(project1, project2);
        when(listProjectPort.listProjects()).thenReturn(projects);

        //when
        List<Project> responses = listProjectService.listProjects();

        //then
        assertThat(responses).isEqualTo(projects);
        verify(listProjectPort, times(1)).listProjects();
        verifyNoMoreInteractions(listProjectPort);
    }
}
