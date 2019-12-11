package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.WorkingOnController;
import de.adesso.squadmap.service.workingOn.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WorkingOnControllerTest {

    @Mock
    private CreateWorkingOnService createWorkingOnService;
    @Mock
    private DeleteWorkingOnService deleteWorkingOnService;
    @Mock
    private GetWorkingOnService getWorkingOnService;
    @Mock
    private ListWorkingOnService listWorkingOnService;
    @Mock
    private UpdateWorkingOnService updateWorkingOnService;
    @InjectMocks
    private WorkingOnController workingOnController;

}
