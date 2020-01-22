package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.GetProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class GetProjectAdapterTest {

    @Autowired
    private GetProjectAdapter getProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        //when
        Project found = getProjectAdapter.getProject(projectId);

        //then
        assertThat(found).isEqualTo(project);
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfGetProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> getProjectAdapter.getProject(projectId));

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }
}
