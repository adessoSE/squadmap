package de.adesso.squadmap.application.domain;

import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommandMother;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectDomainMapperTest {

    @Autowired
    private ProjectDomainMapper projectMapper;

    @Test
    void checkIfMapToDomainEntityMapsCreateCommand() {
        //given
        CreateProjectCommand createProjectCommand = CreateProjectCommandMother.complete().build();

        //when
        Project project = projectMapper.mapToDomainEntity(createProjectCommand);

        //then
        assertThat(project.getProjectId()).isNull();
        assertThat(project.getTitle()).isEqualTo(createProjectCommand.getTitle());
        assertThat(project.getDescription()).isEqualTo(createProjectCommand.getDescription());
        assertThat(project.getSince()).isEqualTo(createProjectCommand.getSince());
        assertThat(project.getUntil()).isEqualTo(createProjectCommand.getUntil());
        assertThat(project.getIsExternal()).isEqualTo(createProjectCommand.getIsExternal());
        assertThat(project.getSites()).isEqualTo(createProjectCommand.getSites());
    }

    @Test
    void checkIfMapToDomainEntityMapsUpdateCommand() {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = UpdateProjectCommandMother.complete().build();

        //when
        Project project = projectMapper.mapToDomainEntity(updateProjectCommand, projectId);

        //then
        assertThat(project.getProjectId()).isEqualTo(projectId);
        assertThat(project.getTitle()).isEqualTo(updateProjectCommand.getTitle());
        assertThat(project.getDescription()).isEqualTo(updateProjectCommand.getDescription());
        assertThat(project.getSince()).isEqualTo(updateProjectCommand.getSince());
        assertThat(project.getUntil()).isEqualTo(updateProjectCommand.getUntil());
        assertThat(project.getIsExternal()).isEqualTo(updateProjectCommand.getIsExternal());
        assertThat(project.getSites()).isEqualTo(updateProjectCommand.getSites());
    }
}
