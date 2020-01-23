package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.WorkingOn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ListWorkingOnAdapterTest {

    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private WorkingOnMapper workingOnMapper;
    @Autowired
    private ListWorkingOnAdapter listWorkingOnAdapter;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = WorkingOn.withId(
                1L, null, null, 0, null, null);
        WorkingOn workingOn2 = WorkingOn.withId(
                2L, null, null, 0, null, null);
        List<WorkingOn> workingOns = Arrays.asList(workingOn1, workingOn2);
        WorkingOnNeo4JEntity workingOnNeo4JEntity1 = new WorkingOnNeo4JEntity(
                1L, null, null, 0, null, null);
        WorkingOnNeo4JEntity workingOnNeo4JEntity2 = new WorkingOnNeo4JEntity(
                2L, null, null, 0, null, null);
        Iterable<WorkingOnNeo4JEntity> workingOnNeo4JEntities = Arrays.asList(workingOnNeo4JEntity1, workingOnNeo4JEntity2);
        when(workingOnRepository.findAll()).thenReturn(workingOnNeo4JEntities);
        when(workingOnMapper.mapToDomainEntity(workingOnNeo4JEntity1)).thenReturn(workingOn1);
        when(workingOnMapper.mapToDomainEntity(workingOnNeo4JEntity2)).thenReturn(workingOn2);

        //when
        List<WorkingOn> found = listWorkingOnAdapter.listWorkingOn();

        //then
        assertThat(found).isEqualTo(workingOns);
        verify(workingOnRepository, times(1)).findAll();
        verify(workingOnMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity1);
        verify(workingOnMapper, times(1)).mapToDomainEntity(workingOnNeo4JEntity2);
        verifyNoMoreInteractions(workingOnRepository);
        verifyNoMoreInteractions(workingOnMapper);
    }
}
