package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.mapper.WorkingOnDomainMapper;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class WorkingOnServiceValidationTest {

    @MockBean
    CreateWorkingOnPort createProjectPort;
    @MockBean
    UpdateWorkingOnPort updateProjectPort;
    @MockBean
    WorkingOnDomainMapper domainMapper;
    @Autowired
    CreateWorkingOnService createProjectService;
    @Autowired
    UpdateWorkingOnService updateProjectService;

    //@Test
    void checkIfCreateWorkingOnRejectsInvalidInput() {

    }

    //@Test
    void checkIfUpdateWorkingOnRejectsInvalidInput() {

    }
}
