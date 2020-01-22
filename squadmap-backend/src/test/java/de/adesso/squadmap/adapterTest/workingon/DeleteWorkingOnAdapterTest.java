package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.DeleteWorkingOnAdapter;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteWorkingOnAdapterTest {

    @Autowired
    private DeleteWorkingOnAdapter deleteWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        doNothing().when(workingOnRepository).deleteById(workingOnId);

        //when
        deleteWorkingOnAdapter.deleteWorkingOn(workingOnId);

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnRepository, times(1)).deleteById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfDeleteWorkingOnThrowsWorkingOnNotFoundException() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> deleteWorkingOnAdapter.deleteWorkingOn(workingOnId));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }
}
