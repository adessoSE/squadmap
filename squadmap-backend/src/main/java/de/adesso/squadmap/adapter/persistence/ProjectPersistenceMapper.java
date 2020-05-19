package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
class ProjectPersistenceMapper implements PersistenceMapper<Project, ProjectNeo4JEntity> {

    public ProjectNeo4JEntity mapToNeo4JEntity(Project project) {
        return ProjectNeo4JEntity.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .since(project.getSince())
                .until(project.getUntil())
                .isExternal(project.getIsExternal())
                .sites(project.getSites())
                .employees(Collections.emptyList())
                .build();
    }

    public Project mapToDomainEntity(ProjectNeo4JEntity projectNeo4JEntity) {
        return Project.builder()
                .projectId(projectNeo4JEntity.getProjectId())
                .title(projectNeo4JEntity.getTitle())
                .description(projectNeo4JEntity.getDescription())
                .since(projectNeo4JEntity.getSince())
                .until(projectNeo4JEntity.getUntil())
                .isExternal(projectNeo4JEntity.getIsExternal())
                .sites(projectNeo4JEntity.getSites())
                .build();
    }
}
