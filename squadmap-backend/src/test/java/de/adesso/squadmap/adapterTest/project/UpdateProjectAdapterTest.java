package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.UpdateProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateProjectAdapterTest {

    @Autowired
    private UpdateProjectAdapter updateProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setTitle("t");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);
        when(projectRepository.save(project)).thenReturn(project);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectRepository, times(1)).save(project);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProjectWithTitleChanged() {
        //given
        long projectId = 1;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setTitle("t");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(false);
        when(projectRepository.save(project)).thenReturn(project);

        //when
        updateProjectAdapter.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verify(projectRepository, times(1)).save(project);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectNotFoundException() {
        //given
        long projectId = 1;
        Project project = new Project();
        project.setProjectId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        assertThrows(ProjectNotFoundException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void checkIfUpdateProjectThrowsProjectAlreadyExistsException() {
        //given
        long projectId = 0;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setTitle("t");
        Project existingProject = new Project();
        existingProject.setTitle("r");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.existsByTitle(project.getTitle())).thenReturn(true);

        //when
        assertThrows(ProjectAlreadyExistsException.class, () -> updateProjectAdapter.updateProject(project));

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).existsByTitle(project.getTitle());
        verifyNoMoreInteractions(projectRepository);
    }
}
