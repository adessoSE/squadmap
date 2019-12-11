package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.ProjectController;
import de.adesso.squadmap.service.project.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectControllerTest {

    @Mock
    private CreateProjectService createProjectService;
    @Mock
    private DeleteProjectService deleteProjectService;
    @Mock
    private GetProjectService getProjectService;
    @Mock
    private ListProjectService listProjectService;
    @Mock
    private UpdateProjectService updateProjectService;
    @InjectMocks
    private ProjectController projectController;
}
