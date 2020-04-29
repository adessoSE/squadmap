package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder
public class GetProjectResponse {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;
    List<GetWorkingOnResponseWithoutProject> employees;

    public List<GetWorkingOnResponseWithoutProject> getEmployees() {
        return new ArrayList<>(this.employees);
    }

    static GetProjectResponse of(Project project, List<WorkingOn> workingOns) {
        return GetProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .since(project.getSince())
                .until(project.getUntil())
                .sites(project.getSites())
                .isExternal(project.getIsExternal())
                .employees(GetWorkingOnResponseWithoutProject.of(workingOns))
                .build();
    }

    public static class GetProjectResponseBuilder {

        public GetProjectResponseBuilder employees(List<GetWorkingOnResponseWithoutProject> employees) {
            this.employees = Optional.ofNullable(employees).stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            return this;
        }
    }
}
