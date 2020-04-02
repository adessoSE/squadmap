package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetWorkingOnService.class)
@ActiveProfiles("test")
class GetWorkingOnServiceTest {

    @MockBean
    private GetWorkingOnPort getWorkingOnPort;
    @Autowired
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
