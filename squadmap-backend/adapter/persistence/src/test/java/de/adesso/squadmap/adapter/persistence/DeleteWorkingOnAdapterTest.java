package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.domain.exceptions.WorkingOnNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DeleteWorkingOnAdapterTest {

    @Mock
    private WorkingOnRepository workingOnRepository;
    @InjectMocks
    private DeleteWorkingOnAdapter deleteWorkingOnAdapter;

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
