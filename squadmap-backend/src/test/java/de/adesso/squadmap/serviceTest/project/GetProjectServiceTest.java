package de.adesso.squadmap.serviceTest.project;

import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.service.project.GetProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GetProjectServiceTest {

    @Autowired
    private GetProjectService service;
    @MockBean
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setUp() {

    }
}
