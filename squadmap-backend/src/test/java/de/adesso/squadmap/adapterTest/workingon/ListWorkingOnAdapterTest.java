package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.ListWorkingOnAdapter;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.repository.WorkingOnRepository;
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

    @Autowired
    private ListWorkingOnAdapter listWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn1 = new WorkingOn();
        workingOn1.setWorkingOnId(1L);
        WorkingOn workingOn2 = new WorkingOn();
        workingOn2.setWorkingOnId(2L);
        Iterable workingOns = Arrays.asList(workingOn1, workingOn2);
        when(workingOnRepository.findAll()).thenReturn(workingOns);

        //when
        List found = listWorkingOnAdapter.listWorkingOn();

        //then
        assertThat(found).isEqualTo(workingOns);
        verify(workingOnRepository, times(1)).findAll();
        verifyNoMoreInteractions(workingOnRepository);
    }
}
