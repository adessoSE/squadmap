package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
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

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private WorkingOnMapper workingOnMapper;
    @Autowired
    private UpdateWorkingOnAdapter updateWorkingOnAdapter;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOn.withId(
                workingOnId, null, null, 0, null, null);
        WorkingOnNeo4JEntity workingOnNeo4JEntity = new WorkingOnNeo4JEntity(
                workingOnId, null, null, 0, null, null);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(true);
        when(workingOnMapper.mapToNeo4JEntity(workingOn)).thenReturn(workingOnNeo4JEntity);
        when(workingOnRepository.save(workingOnNeo4JEntity)).thenReturn(workingOnNeo4JEntity);

        //when
        updateWorkingOnAdapter.updateWorkingOn(workingOn);

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verify(workingOnMapper, times(1)).mapToNeo4JEntity(workingOn);
        verify(workingOnRepository, times(1)).save(workingOnNeo4JEntity);
        verifyNoMoreInteractions(workingOnMapper);
        verifyNoMoreInteractions(workingOnRepository);
    }

    @Test
    void checkIfUpdateWorkingOnThrowsWorkingOnNotFoundException() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOn.withId(
                workingOnId, null, null, 0, null, null);
        when(workingOnRepository.existsById(workingOnId)).thenReturn(false);

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> updateWorkingOnAdapter.updateWorkingOn(workingOn));

        //then
        verify(workingOnRepository, times(1)).existsById(workingOnId);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoInteractions(workingOnMapper);
    }
}
