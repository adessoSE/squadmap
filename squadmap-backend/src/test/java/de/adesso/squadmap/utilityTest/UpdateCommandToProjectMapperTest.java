package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.utility.UpdateCommandToProjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateCommandToProjectMapperTest {

    @Autowired
    private UpdateCommandToProjectMapper projectMapper;

    @Test
    void checkIfMapMapsUpdateCommandToValidProject() {
        //given
        UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand(
                "t", "d", LocalDate.now(), LocalDate.now(), true, Lists.emptyList());

        //when
        Project project = projectMapper.map(updateProjectCommand);

        //then
        assertThat(project.getTitle()).isEqualTo(updateProjectCommand.getTitle());
        assertThat(project.getDescription()).isEqualTo(updateProjectCommand.getDescription());
        assertThat(project.getSince()).isEqualTo(updateProjectCommand.getSince());
        assertThat(project.getUntil()).isEqualTo(updateProjectCommand.getUntil());
        assertThat(project.getIsExternal()).isEqualTo(updateProjectCommand.isExternal());
        assertThat(project.getSites()).isEqualTo(updateProjectCommand.getSites());
    }
}
