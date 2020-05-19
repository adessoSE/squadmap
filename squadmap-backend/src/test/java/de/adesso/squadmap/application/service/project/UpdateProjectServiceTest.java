package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.domain.mapper.ProjectDomainMapper;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommandMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UpdateProjectServiceTest {

    @Mock
    private UpdateProjectPort updateProjectPort;
    @Mock
    private ProjectDomainMapper projectDomainMapper;
    @InjectMocks
    private UpdateProjectService updateProjectService;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = UpdateProjectCommandMother.complete().build();
        Project project = ProjectMother.complete().build();
        when(projectDomainMapper.mapToDomainEntity(updateProjectCommand, projectId)).thenReturn(project);
        doNothing().when(updateProjectPort).updateProject(project);

        //when
        updateProjectService.updateProject(updateProjectCommand, projectId);

        //then
        verify(projectDomainMapper, times(1)).mapToDomainEntity(updateProjectCommand, projectId);
        verify(updateProjectPort, times(1)).updateProject(project);
        verifyNoMoreInteractions(projectDomainMapper);
        verifyNoMoreInteractions(updateProjectPort);
    }
}
