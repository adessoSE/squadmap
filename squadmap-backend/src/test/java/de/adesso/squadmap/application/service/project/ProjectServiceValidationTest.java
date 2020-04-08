package de.adesso.squadmap.application.service.project;

import de.adesso.squadmap.application.domain.mapper.ProjectDomainMapper;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driven.project.UpdateProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProjectServiceValidationTest {

    @MockBean
    CreateProjectPort createProjectPort;
    @MockBean
    UpdateProjectPort updateProjectPort;
    @MockBean
    ProjectDomainMapper domainMapper;
    @Autowired
    CreateProjectService createProjectService;
    @Autowired
    UpdateProjectService updateProjectService;

    //@Test
    void checkIfCreateProjectRejectsInvalidInput() {

    }

    //@Test
    void checkIfUpdateProjectRejectsInvalidInput() {

    }

}
