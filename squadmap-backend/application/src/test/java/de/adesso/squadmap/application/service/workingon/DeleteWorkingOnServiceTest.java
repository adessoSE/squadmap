package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driven.workingon.DeleteWorkingOnPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DeleteWorkingOnServiceTest {

    @Mock
    private DeleteWorkingOnPort deleteWorkingOnPort;
    @InjectMocks
    private DeleteWorkingOnService deleteWorkingOnService;

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnPort).deleteWorkingOn(workingOnId);

        //when
        deleteWorkingOnService.deleteWorkingOn(workingOnId);

        //then
        verify(deleteWorkingOnPort, times(1)).deleteWorkingOn(workingOnId);
        verifyNoMoreInteractions(deleteWorkingOnPort);
    }
}
