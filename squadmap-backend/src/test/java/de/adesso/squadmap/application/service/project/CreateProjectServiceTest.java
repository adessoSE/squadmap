package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.domain.mapper.ProjectDomainMapper;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommandMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CreateProjectServiceTest {

    @Mock
    private CreateProjectPort createProjectPort;
    @Mock
    private ProjectDomainMapper projectDomainMapper;
    @InjectMocks
    private CreateProjectService createProjectService;

    @Test
    void checkIfCreateProjectCreatesAProject() {
        //given
        long projectId = 1;
        CreateProjectCommand createProjectCommand = CreateProjectCommandMother.complete().build();
        Project project = ProjectMother.complete().build();
        when(projectDomainMapper.mapToDomainEntity(createProjectCommand)).thenReturn(project);
        when(createProjectPort.createProject(project)).thenReturn(projectId);

        //when
        long found = createProjectService.createProject(createProjectCommand);

        //then
        assertThat(found).isEqualTo(projectId);
        verify(projectDomainMapper, times(1)).mapToDomainEntity(createProjectCommand);
        verify(createProjectPort, times(1)).createProject(project);
        verifyNoMoreInteractions(projectDomainMapper);
        verifyNoMoreInteractions(createProjectPort);
    }
}
