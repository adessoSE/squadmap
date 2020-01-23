package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Project;
import org.springframework.stereotype.Component;

@Component
class ProjectMapper {

    ProjectNeo4JEntity mapToNeo4JEntity(Project project) {
        return new ProjectNeo4JEntity(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites());
    }

    Project mapToDomainEntity(ProjectNeo4JEntity projectNeo4JEntity) {
        return Project.withId(
                projectNeo4JEntity.getProjectId(),
                projectNeo4JEntity.getTitle(),
                projectNeo4JEntity.getDescription(),
                projectNeo4JEntity.getSince(),
                projectNeo4JEntity.getUntil(),
                projectNeo4JEntity.getIsExternal(),
                projectNeo4JEntity.getSites());
    }
}
