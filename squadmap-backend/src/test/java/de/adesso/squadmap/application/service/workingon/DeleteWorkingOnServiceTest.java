package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driven.workingon.DeleteWorkingOnPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = DeleteWorkingOnService.class)
@ActiveProfiles("test")
class DeleteWorkingOnServiceTest {

    @MockBean
    private DeleteWorkingOnPort deleteWorkingOnPort;
    @Autowired
    private DeleteWorkingOnService service;

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnPort).deleteWorkingOn(workingOnId);

        //when
        service.deleteWorkingOn(workingOnId);

        //then
        verify(deleteWorkingOnPort, times(1)).deleteWorkingOn(workingOnId);
        verifyNoMoreInteractions(deleteWorkingOnPort);
    }
}
