package de.adesso.squadmap.adapterTest.project;

import de.adesso.squadmap.adapter.project.UpdateProjectAdapter;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateProjectAdapterTest {

    @Autowired
    private UpdateProjectAdapter updateProjectAdapter;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void checkIfUpdateProjectUpdatesTheProject() {

    }

    @Test
    void checkIfUpdateProjectThrowsProjectNotFoundException() {

    }
}
