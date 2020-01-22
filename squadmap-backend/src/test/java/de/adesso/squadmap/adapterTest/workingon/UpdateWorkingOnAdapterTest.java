package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.UpdateWorkingOnAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateWorkingOnAdapterTest {

    @Autowired
    private UpdateWorkingOnAdapter updateWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = new WorkingOn();
        workingOn.setWorkingOnId(workingOnId);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(workingOnRepository.save(workingOn)).thenReturn(workingOn);

        //when
        updateWorkingOnAdapter.updateWorkingOn(workingOn);

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnRepository, times(1)).save(workingOn);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsWorkingOnNotFoundException() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = new WorkingOn();
        workingOn.setWorkingOnId(workingOnId);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
