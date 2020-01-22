package de.adesso.squadmap.serviceTest.workingon;

import de.adesso.squadmap.adapter.project.DeleteProjectAdapter;
import de.adesso.squadmap.adapter.workingon.DeleteWorkingOnAdapter;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingon.DeleteWorkingOnService;
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
    private DeleteWorkingOnAdapter deleteWorkingOnAdapter;

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnAdapter).deleteWorkingOn(workingOnId);

        //when
        service.deleteWorkingOn(workingOnId);

        //then
        verify(deleteWorkingOnAdapter, times(1)).deleteWorkingOn(workingOnId);
        verifyNoMoreInteractions(deleteWorkingOnAdapter);
    }
}
