package de.adesso.squadmap.serviceTest.workingOn;

import de.adesso.squadmap.exceptions.workingOn.WorkingOnNotFoundException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingOn.DeleteWorkingOnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DeleteWorkingOnServiceTest {

    @Autowired
    private DeleteWorkingOnService service;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        doNothing().when(workingOnRepository).deleteById(workingOnId);

        //when
        service.deleteWorkingOn(workingOnId);

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnRepository, times(1)).deleteById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfDeleteWorkingOnThrowsExceptionWhenNotFound() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //then
        assertThrows(WorkingOnNotFoundException.class, () ->
                service.deleteWorkingOn(workingOnId));
    }
}
