package de.adesso.squadmap.application.domain;

import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommandMother;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WorkingOnDomainMapper.class)
@ActiveProfiles("test")
public class WorkingOnDomainMapperTest {

    @Autowired
    private WorkingOnDomainMapper workingOnMapper;

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
