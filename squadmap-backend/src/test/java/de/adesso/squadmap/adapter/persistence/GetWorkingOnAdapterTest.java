package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GetWorkingOnAdapter.class)
@ActiveProfiles("test")
public class GetWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> workingOnPersistenceMapper;
    @Autowired
    private GetWorkingOnAdapter getWorkingOnAdapter;

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOnMother.complete()
                .workingOnId(workingOnId)
                .build();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete().build();
        when(workingOnRepository.findById(workingOnId, 0)).thenReturn(Optional.of(workingOnNeo4JEntity));
        when(workingOnPersistenceMapper.mapToDomainEntity(workingOnNeo4JEntity)).thenReturn(workingOn);

        //when
        WorkingOn found = getWorkingOnAdapter.getWorkingOn(workingOnId);

        //then
        assertThat(found).isEqualTo(workingOn);
        verify(workingOnRepository, times(1)).findById(workingOnId, 0);
        verify(workingOnPersistenceMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoMoreInteractions(workingOnPersistenceMapper);
    }

    @Test
    void checkIfGetWorkingOnThrowsNotFoundException() {
        //given
        long workingOnId = 1;
        when(workingOnRepository.findById(workingOnId, 0)).thenReturn(Optional.empty());

        //when
        assertThrows(WorkingOnNotFoundException.class, () -> getWorkingOnAdapter.getWorkingOn(workingOnId));

        //then
        verify(workingOnRepository, times(1)).findById(workingOnId, 0);
        verifyNoMoreInteractions(workingOnRepository);
    }

}
