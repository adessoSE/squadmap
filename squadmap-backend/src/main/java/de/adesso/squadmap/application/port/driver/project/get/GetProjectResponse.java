package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "GetProjectResponseBuilder")
@JsonDeserialize(builder = GetProjectResponse.GetProjectResponseBuilder.class)
public class GetProjectResponse {

    private final Long projectId;
    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List<String> sites;
    private final List<GetWorkingOnResponseWithoutProject> employees;

    public static GetProjectResponse getInstance(Project project, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutProject> getWorkingOnResponseWithoutProjects = new ArrayList<>();
        workingOns.forEach(workingOn -> {
            if (Objects.nonNull(workingOn.getProject()) && workingOn.getProject().getProjectId().equals(project.getProjectId())) {
                getWorkingOnResponseWithoutProjects.add(GetWorkingOnResponseWithoutProject.getInstance(workingOn));
            }
        });
        return new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites(),
                getWorkingOnResponseWithoutProjects);
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetProjectResponseBuilder {
    }
}
