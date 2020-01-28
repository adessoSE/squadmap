package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectDomainMapper;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommandMother;
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
    private CreateProjectPort createProjectPort;
    @MockBean
    private ProjectDomainMapper projectMapper;

    @Test
    void checkIfCreateProjectCreatesAProject() {
        //given
        long projectId = 1;
        CreateProjectCommand createProjectCommand = CreateProjectCommandMother.complete().build();
        Project project = ProjectMother.complete().build();
        when(projectMapper.mapToDomainEntity(createProjectCommand)).thenReturn(project);
        when(createProjectPort.createProject(project)).thenReturn(projectId);

        //when
        long found = service.createProject(createProjectCommand);

        //then
        assertThat(found).isEqualTo(projectId);
        verify(projectMapper, times(1)).mapToDomainEntity(createProjectCommand);
        verify(createProjectPort, times(1)).createProject(project);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(createProjectPort);
    }
}
