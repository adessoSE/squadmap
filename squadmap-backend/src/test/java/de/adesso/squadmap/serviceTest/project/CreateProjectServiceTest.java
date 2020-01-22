package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.adapter.project.CreateProjectAdapter;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.service.project.CreateProjectService;
import de.adesso.squadmap.utility.CreateCommandToProjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateProjectServiceTest {

    @Autowired
    private CreateProjectService service;
    @MockBean
    private CreateProjectAdapter createProjectAdapter;
    @MockBean
    private CreateCommandToProjectMapper projectMapper;

    @Test
    void checkIfCreateProjectCreatesAProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        CreateProjectCommand createProjectCommand = new CreateProjectCommand();
        when(projectMapper.map(createProjectCommand)).thenReturn(project);
        when(createProjectAdapter.createProject(project)).thenReturn(projectId);

        //when
        long found = service.createProject(createProjectCommand);

        //then
        assertThat(found).isEqualTo(projectId);
        verify(projectMapper, times(1)).map(createProjectCommand);
        verify(createProjectAdapter, times(1)).createProject(project);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(createProjectAdapter);
    }
}
