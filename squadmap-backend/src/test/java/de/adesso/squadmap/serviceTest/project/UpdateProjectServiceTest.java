package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.UpdateProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class UpdateProjectServiceTest {

    @Autowired
    private UpdateProjectService service;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        UpdateProjectCommand command = new UpdateProjectCommand("", "", LocalDate.now(), LocalDate.now(), true);
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.when(projectRepository.existsByTitle(command.getTitle())).thenReturn(false);
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(project)).thenReturn(project);

        //when
        service.updateProject(command, projectId);

        //then
        assertThat(project.getTitle()).isEqualTo(command.getTitle());
        assertThat(project.getDescription()).isEqualTo(command.getDescription());
        assertThat(project.getSince()).isEqualTo(command.getSince());
        assertThat(project.getUntil()).isEqualTo(command.getUntil());
        assertThat(project.getIsExternal()).isEqualTo(command.getIsExternal());
        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void checkIfUpdateProjectThrowsExceptionWhenNotFound() {
        //given
        long id = 1;
        UpdateProjectCommand command = new UpdateProjectCommand();
        Mockito.when(projectRepository.existsById(id)).thenReturn(false);

        //then
        assertThrows(ProjectNotFoundException.class, () ->
                service.updateProject(command, id));
    }

    @Test
    void checkIfUpdateProjectThrowsExceptionWhenTitleAlreadyExists() {
        //given
        long projectId = 1;
        Project project = new Project();
        UpdateProjectCommand command = new UpdateProjectCommand();
        project.setTitle("a");
        command.setTitle("b");
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.existsByTitle(command.getTitle())).thenReturn(true);

        //then
        assertThrows(ProjectAlreadyExistsException.class, () ->
                service.updateProject(command, projectId));
    }
}
