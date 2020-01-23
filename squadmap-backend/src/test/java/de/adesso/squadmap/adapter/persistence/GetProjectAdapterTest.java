package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class GetProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectMapper projectMapper;
    @Autowired
    private GetProjectAdapter getProjectAdapter;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = Project.withId(
                1L, "", "", null, null, true, null);
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                1L, "", "", null, null, true, new ArrayList<>());
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectMapper.mapToDomainEntity(projectNeo4JEntity)).thenReturn(project);

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
