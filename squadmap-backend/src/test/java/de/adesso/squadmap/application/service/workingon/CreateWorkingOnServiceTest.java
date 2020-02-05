package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.*;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateWorkingOnServiceTest {

    @Autowired
    private CreateWorkingOnService service;
    @MockBean
    private CreateWorkingOnPort createWorkingOnPort;
    @MockBean
    private GetEmployeePort getEmployeePort;
    @MockBean
    private GetProjectPort getProjectPort;
    @MockBean
    private WorkingOnDomainMapper workingOnMapper;

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() {
        //given
        long employeeId = 1;
        long projectId = 2;
        long workingOnId = 3;
        CreateWorkingOnCommand createWorkingOnCommand = CreateWorkingOnCommandMother.complete()
                .employeeId(employeeId)
                .projectId(projectId)
                .build();
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .build();
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .build();
        WorkingOn workingOn = WorkingOnMother.complete().build();
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectPort.getProject(projectId)).thenReturn(project);
        when(workingOnMapper.mapToDomainEntity(createWorkingOnCommand, employee, project)).thenReturn(workingOn);
        when(createWorkingOnPort.createWorkingOn(workingOn)).thenReturn(workingOnId);

        //when
        long found = service.createWorkingOn(createWorkingOnCommand);

        //then
        assertThat(found).isEqualTo(workingOnId);
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(getProjectPort, times(1)).getProject(projectId);
        verify(workingOnMapper, times(1)).mapToDomainEntity(createWorkingOnCommand, employee, project);
        verify(createWorkingOnPort, times(1)).createWorkingOn(workingOn);
        verifyNoMoreInteractions(getEmployeePort);
        verifyNoMoreInteractions(getProjectPort);
        verifyNoMoreInteractions(workingOnMapper);
        verifyNoMoreInteractions(createWorkingOnPort);
    }
}
