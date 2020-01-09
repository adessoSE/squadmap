package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.CreateProjectService;
import de.adesso.squadmap.utility.CreateCommandToProjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateProjectServiceTest {

    @Autowired
    private CreateProjectService service;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private CreateCommandToProjectMapper projectMapper;

    @Test
    void checkIfCreateProjectCreatesAProject() {
        //given
        String title = "";
        Project project = new Project();
        project.setProjectId(1L);
        CreateProjectCommand command = new CreateProjectCommand();
        command.setTitle(title);
        when(projectRepository.existsByTitle(title)).thenReturn(false);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.map(command)).thenReturn(project);

        //when
        long found = service.createProject(command);

        //then
        assertThat(found).isEqualTo(project.getProjectId());
        verify(projectRepository, times(1)).existsByTitle(title);
        verify(projectRepository, times(1)).save(project);
        verify(projectMapper, times(1)).map(command);
        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(projectMapper);
    }

    @Test
    void checkIfCreateProjectThrowsExceptionWhenTitleAlreadyExists() {
        //given
        String title = "";
        CreateProjectCommand command = new CreateProjectCommand();
        command.setTitle(title);
        when(projectRepository.existsByTitle(title)).thenReturn(true);

        //then
        assertThrows(ProjectAlreadyExistsException.class, () ->
                service.createProject(command));
    }
}
