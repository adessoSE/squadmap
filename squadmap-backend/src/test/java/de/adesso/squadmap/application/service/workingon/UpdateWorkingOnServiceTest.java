package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.*;
import de.adesso.squadmap.application.domain.mapper.WorkingOnDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.project.GetProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommandMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UpdateWorkingOnServiceTest {

    @Mock
    private UpdateWorkingOnPort updateWorkingOnPort;
    @Mock
    private GetEmployeePort getEmployeePort;
    @Mock
    private GetProjectPort getProjectPort;
    @Mock
    private WorkingOnDomainMapper workingOnDomainMapper;
    @InjectMocks
    private UpdateWorkingOnService updateWorkingOnService;

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() {
        //given
        long workingOnId = 1;
        long employeeId = 1;
        long projectId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .build();
        Project project = ProjectMother.complete()
                .projectId(projectId)
                .build();
        UpdateWorkingOnCommand updateWorkingOnCommand = UpdateWorkingOnCommandMother.complete()
                .employeeId(employeeId)
                .projectId(projectId)
                .build();
        WorkingOn workingOn = WorkingOnMother.complete().build();
        when(getEmployeePort.getEmployee(employeeId)).thenReturn(employee);
        when(getProjectPort.getProject(projectId)).thenReturn(project);
        when(workingOnDomainMapper.mapToDomainEntity(updateWorkingOnCommand, workingOnId, employee, project)).thenReturn(workingOn);
        doNothing().when(updateWorkingOnPort).updateWorkingOn(workingOn);

        //when
        updateWorkingOnService.updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //then
        verify(getEmployeePort, times(1)).getEmployee(employeeId);
        verify(getProjectPort, times(1)).getProject(projectId);
        verify(workingOnDomainMapper, times(1)).mapToDomainEntity(updateWorkingOnCommand, workingOnId, employee, project);
        verify(updateWorkingOnPort, times(1)).updateWorkingOn(workingOn);
        verifyNoMoreInteractions(getEmployeePort);
        verifyNoMoreInteractions(getProjectPort);
        verifyNoMoreInteractions(workingOnDomainMapper);
        verifyNoMoreInteractions(updateWorkingOnPort);
    }
}
