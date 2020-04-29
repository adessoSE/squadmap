package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Project;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
class GetProjectResponseWithoutEmployee {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;

    static GetProjectResponseWithoutEmployee of(Project project) {
        return GetProjectResponseWithoutEmployee.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .since(project.getSince())
                .until(project.getUntil())
                .sites(project.getSites())
                .isExternal(project.getIsExternal())
                .build();
    }
}
