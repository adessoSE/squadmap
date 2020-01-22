package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.GetWorkingOnAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class GetWorkingOnAdapterTest {

    @Autowired
    private GetWorkingOnAdapter getWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = new WorkingOn();
        when(workingOnRepository.findById(workingOnId)).thenReturn(Optional.of(workingOn));

        //when
        WorkingOn found = getWorkingOnAdapter.getWorkingOn(workingOnId);

        //then
        assertThat(found).isEqualTo(workingOn);
        verify(workingOnRepository, times(1)).findById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfGetWorkingOnThrowsNotFoundException() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.findById(workingOnId)).thenReturn(Optional.empty());

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> getWorkingOnAdapter.getWorkingOn(workingOnId));

        //then
        verify(workingOnRepository, times(1)).findById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }

}
