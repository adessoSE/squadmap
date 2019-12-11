package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.GetProjectService;
import de.adesso.squadmap.utility.ProjectToResponseMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class GetProjectServiceTest {

    @Autowired
    private GetProjectService service;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectToResponseMapper projectMapper;

    @Test
    void checkIfGetProjectReturnsTheProject() {
        //given
        long projectId = 1;
        Project project = new Project();
        GetProjectResponse response = new GetProjectResponse();
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(projectMapper.map(project)).thenReturn(response);

        //when
        GetProjectResponse found = service.getProject(projectId);

        //then
        assertThat(found).isEqualTo(response);
        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectMapper, times(1)).map(project);
    }

    @Test
    void checkIfGetProjectThrowsExceptionWhenNotFound() {
        //given
        long projectId = 1;
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(false);

        //then
        assertThrows(ProjectNotFoundException.class, () ->
                service.getProject(projectId));
    }
}
