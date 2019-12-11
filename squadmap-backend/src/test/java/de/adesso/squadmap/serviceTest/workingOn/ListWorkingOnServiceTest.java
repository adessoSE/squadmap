package de.adesso.squadmap.serviceTest.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingOn.ListWorkingOnService;
import de.adesso.squadmap.utility.WorkingOnToResponseMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ListWorkingOnServiceTest {

    @Autowired
    private ListWorkingOnService service;
    @MockBean
    private WorkingOnRepository workingOnRepository;
    @MockBean
    private WorkingOnToResponseMapper workingOnMapper;

    @Test
    void checkIfListWorkingOnReturnsAllRelations() {
        //given
        WorkingOn workingOn = new WorkingOn();
        GetWorkingOnResponse response = new GetWorkingOnResponse();
        List<WorkingOn> allWorkingOn = Collections.singletonList(workingOn);
        Mockito.when(workingOnRepository.findAll()).thenReturn(allWorkingOn);
        Mockito.when(workingOnMapper.map(workingOn)).thenReturn(response);

        //when
        List<GetWorkingOnResponse> found = service.listWorkingOn();

        //then
        assertThat(found).isEqualTo(Collections.singletonList(response));
        verify(workingOnRepository, times(1)).findAll();
        verify(workingOnMapper, times(1)).map(workingOn);
    }
}
