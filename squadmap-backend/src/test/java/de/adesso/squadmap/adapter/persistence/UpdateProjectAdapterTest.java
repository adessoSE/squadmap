package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateProjectAdapterTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectMapper projectMapper;
    @Autowired
    private UpdateProjectAdapter updateProjectAdapter;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        Project project = Project.withId(
                1L, "t", "", null, null, true, null);
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                projectId, "t", "", null, null, true, new ArrayList<>());
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);
        when(projectMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity)).thenReturn(projectNeo4JEntity);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectMapper, times(1)).mapToNeo4JEntity(project);
        verify(projectRepository, times(1)).save(projectNeo4JEntity);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProjectWithTitleChanged() {
        //given
        long projectId = 1;
        Project project = Project.withId(
                1L, "t", "", null, null, true, null);
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                projectId, "r", "", null, null, true, new ArrayList<>());
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectNeo4JEntity));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectMapper.mapToNeo4JEntity(project)).thenReturn(projectNeo4JEntity);
        when(projectRepository.save(projectNeo4JEntity)).thenReturn(projectNeo4JEntity);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectMapper, times(1)).mapToNeo4JEntity(project);
        verify(projectRepository, times(1)).save(projectNeo4JEntity);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        Project project = Project.withId(
                1L, "", "", null, null, true, null);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(projectMapper);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectAlreadyExistsException() {
        //given
        long projectId = 0;
        Project project = Project.withId(
                projectId, "t", "", null, null, true, null);
        ProjectNeo4JEntity existingProject = new ProjectNeo4JEntity(
                projectId, "r", "", null, null, true, new ArrayList<>());
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
        verifyNoInteractions(projectMapper);
    }
}
