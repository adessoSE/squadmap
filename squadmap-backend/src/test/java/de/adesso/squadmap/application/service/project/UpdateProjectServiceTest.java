package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectDomainMapper;
import de.adesso.squadmap.application.domain.ProjectMother;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateProjectServiceTest {

    @Autowired
    private UpdateProjectService service;
    @MockBean
    private UpdateProjectPort updateProjectPort;
    @MockBean
    private ProjectDomainMapper projectMapper;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = UpdateProjectCommandMother.complete().build();
        Project project = ProjectMother.complete().build();
        when(projectMapper.mapToDomainEntity(updateProjectCommand, projectId)).thenReturn(project);
        doNothing().when(updateProjectPort).updateProject(project);

        //when
        service.updateProject(updateProjectCommand, projectId);

        //then
        verify(projectMapper, times(1)).mapToDomainEntity(updateProjectCommand, projectId);
        verify(updateProjectPort, times(1)).updateProject(project);
        verifyNoMoreInteractions(projectMapper);
        verifyNoMoreInteractions(updateProjectPort);
    }
}
