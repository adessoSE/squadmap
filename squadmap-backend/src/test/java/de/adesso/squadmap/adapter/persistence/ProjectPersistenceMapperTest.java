package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ProjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectPersistenceMapperTest {

    @Autowired
    private ProjectPersistenceMapper projectPersistenceMapper;

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntity() {
        //given
        Project project = ProjectMother.complete().build();

        //when
        ProjectNeo4JEntity projectNeo4JEntity = projectPersistenceMapper.mapToNeo4JEntity(project);

        //then
        assertThat(projectNeo4JEntity.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(projectNeo4JEntity.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectNeo4JEntity.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectNeo4JEntity.getSince()).isEqualTo(project.getSince());
        assertThat(projectNeo4JEntity.getUntil()).isEqualTo(project.getUntil());
        assertThat(projectNeo4JEntity.getIsExternal()).isEqualTo(project.getIsExternal());
        assertThat(projectNeo4JEntity.getSites()).isEqualTo(project.getSites());
        assertThat(projectNeo4JEntity.getEmployees()).isEmpty();
    }

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntityWithList() {
        //given
        Project project = ProjectMother.complete().build();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete()
                .project(ProjectNeo4JEntityMother.complete()
                        .projectId(project.getProjectId())
                        .build())
                .build();
        List<WorkingOnNeo4JEntity> relations = Collections.singletonList(workingOnNeo4JEntity);

        //when
        ProjectNeo4JEntity projectNeo4JEntity = projectPersistenceMapper.mapToNeo4JEntity(project, relations);

        //then
        assertThat(projectNeo4JEntity.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(projectNeo4JEntity.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectNeo4JEntity.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectNeo4JEntity.getSince()).isEqualTo(project.getSince());
        assertThat(projectNeo4JEntity.getUntil()).isEqualTo(project.getUntil());
        assertThat(projectNeo4JEntity.getIsExternal()).isEqualTo(project.getIsExternal());
        assertThat(projectNeo4JEntity.getSites()).isEqualTo(project.getSites());
        assertThat(projectNeo4JEntity.getEmployees()).isEqualTo(relations);
    }

    @Test
    void checkIfMapToDomainEntityMapsToValidEntityFromNeo4JEntity() {
        //given
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();

        //when
        Project project = projectPersistenceMapper.mapToDomainEntity(projectNeo4JEntity);

        //then
        assertThat(project.getProjectId()).isEqualTo(projectNeo4JEntity.getProjectId());
        assertThat(project.getTitle()).isEqualTo(projectNeo4JEntity.getTitle());
        assertThat(project.getDescription()).isEqualTo(projectNeo4JEntity.getDescription());
        assertThat(project.getSince()).isEqualTo(projectNeo4JEntity.getSince());
        assertThat(project.getUntil()).isEqualTo(projectNeo4JEntity.getUntil());
        assertThat(project.getIsExternal()).isEqualTo(projectNeo4JEntity.getIsExternal());
        assertThat(project.getSites()).isEqualTo(projectNeo4JEntity.getSites());
    }
}
