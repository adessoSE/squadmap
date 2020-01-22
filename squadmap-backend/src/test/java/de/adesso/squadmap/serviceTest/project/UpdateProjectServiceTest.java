package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.adapter.project.UpdateProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.project.ProjectAlreadyExistsException;
import de.adesso.squadmap.exceptions.project.ProjectNotFoundException;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.UpdateProjectService;
import de.adesso.squadmap.utility.UpdateCommandToProjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateProjectServiceTest {

    @Autowired
    private UpdateProjectService service;
    @MockBean
    private UpdateProjectAdapter updateProjectAdapter;
    @MockBean
    private UpdateCommandToProjectMapper projectMapper;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand();
        when(projectMapper.map(updateProjectCommand)).thenReturn(project);
        doNothing().when(updateProjectAdapter).updateProject(project);

        //when
        service.updateProject(updateProjectCommand, projectId);

        //then
        assertThat(project.getProjectId()).isEqualTo(projectId);
        verify(projectMapper, times(1)).map(updateProjectCommand);
        verify(updateProjectAdapter, times(1)).updateProject(project);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(updateProjectAdapter);
    }
}
