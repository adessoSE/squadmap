package de.adesso.squadmap.serviceTest.workingOn;

import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.service.workingOn.ListWorkingOnService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GetWorkingOnTest {

    @Autowired
    private ListWorkingOnService service;
    @MockBean
    private WorkingOnRepository workingOnRepository;

    @BeforeEach
    public void setUp() {

    }
}
