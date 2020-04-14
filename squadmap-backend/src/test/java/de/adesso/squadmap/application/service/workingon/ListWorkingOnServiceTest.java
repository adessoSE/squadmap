package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ListWorkingOnServiceTest {

    @Mock
    private ListWorkingOnPort listWorkingOnPort;
    @InjectMocks
    private ListWorkingOnService listWorkingOnService;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = WorkingOnMother.complete()
                .workingOnId(1L)
                .build();
        WorkingOn workingOn2 = WorkingOnMother.complete()
                .workingOnId(2L)
                .build();
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        when(listWorkingOnPort.listWorkingOn()).thenReturn(workingOns);

        //when
        List<WorkingOn> responses = listWorkingOnService.listWorkingOn();

        //then
        assertThat(responses).isEqualTo(workingOns);
        verify(listWorkingOnPort, times(1)).listWorkingOn();
        verifyNoMoreInteractions(listWorkingOnPort);
    }
}
