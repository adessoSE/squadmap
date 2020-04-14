package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
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
class GetWorkingOnServiceTest {

    @Mock
    private GetWorkingOnPort getWorkingOnPort;
    @InjectMocks
    private GetWorkingOnService getWorkingOnService;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOnMother.complete()
                .workingOnId(workingOnId)
                .build();
        when(getWorkingOnPort.getWorkingOn(workingOnId)).thenReturn(workingOn);

        //when
        WorkingOn response = getWorkingOnService.getWorkingOn(workingOnId);

        //then
        assertThat(response).isEqualTo(workingOn);
        verify(getWorkingOnPort, times(1)).getWorkingOn(workingOnId);
        verifyNoMoreInteractions(getWorkingOnPort);
    }
}
