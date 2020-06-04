package de.adesso.squadmap.domain.mapper.implementation;

import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommandMother;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommandMother;
import de.adesso.squadmap.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class WorkingOnDomainMapperImplementationTest {

    private WorkingOnDomainMapperImplementation workingOnMapper = new WorkingOnDomainMapperImplementation();

    @Test
    void checkIfMapToDomainEntityMapsCreateCommand() {
        //given
        CreateWorkingOnCommand createWorkingOnCommand = CreateWorkingOnCommandMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        Project project = ProjectMother.complete().build();

        //when
        WorkingOn workingOn = workingOnMapper.mapToDomainEntity(createWorkingOnCommand, employee, project);

        //then
        assertThat(workingOn.getWorkingOnId()).isNull();
        assertThat(workingOn.getSince()).isEqualTo(createWorkingOnCommand.getSince());
        assertThat(workingOn.getUntil()).isEqualTo(createWorkingOnCommand.getUntil());
        assertThat(workingOn.getWorkload()).isEqualTo(createWorkingOnCommand.getWorkload());
        assertThat(workingOn.getEmployee()).isEqualTo(employee);
        assertThat(workingOn.getProject()).isEqualTo(project);
    }

    @Test
    void checkIfMapToDomainEntityMapsUpdateCommand() {
        //given
        long workingOnId = 1;
        UpdateWorkingOnCommand createWorkingOnCommand = UpdateWorkingOnCommandMother.complete().build();
        Employee employee = EmployeeMother.complete().build();
        Project project = ProjectMother.complete().build();

        //when
        WorkingOn workingOn = workingOnMapper.mapToDomainEntity(createWorkingOnCommand, workingOnId, employee, project);

        //then
        assertThat(workingOn.getWorkingOnId()).isEqualTo(workingOnId);
        assertThat(workingOn.getSince()).isEqualTo(createWorkingOnCommand.getSince());
        assertThat(workingOn.getUntil()).isEqualTo(createWorkingOnCommand.getUntil());
        assertThat(workingOn.getWorkload()).isEqualTo(createWorkingOnCommand.getWorkload());
        assertThat(workingOn.getEmployee()).isEqualTo(employee);
        assertThat(workingOn.getProject()).isEqualTo(project);
    }
}
