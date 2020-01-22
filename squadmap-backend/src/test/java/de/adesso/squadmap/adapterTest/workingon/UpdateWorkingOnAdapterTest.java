package de.adesso.squadmap.adapterTest.workingon;

import de.adesso.squadmap.adapter.workingon.UpdateWorkingOnAdapter;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateWorkingOnAdapterTest {

    @Autowired
    private UpdateWorkingOnAdapter updateWorkingOnAdapter;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {

    }

    @Test
    void checkIfUpdateWorkingOnThrowsWorkingOnNotFoundException() {

    }
}
