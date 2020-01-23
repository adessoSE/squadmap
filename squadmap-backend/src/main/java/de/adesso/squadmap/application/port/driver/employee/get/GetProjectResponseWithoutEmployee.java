package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.Project;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(builderClassName = "GetProjectResponseWithoutEmployeeBuilder")
@JsonDeserialize(builder = GetProjectResponseWithoutEmployee.GetProjectResponseWithoutEmployeeBuilder.class)
class GetProjectResponseWithoutEmployee {

    private final Long projectId;
    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List<String> sites;

    static GetProjectResponseWithoutEmployee getInstance(Project project) {
        return new GetProjectResponseWithoutEmployee(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetProjectResponseWithoutEmployeeBuilder { }
}
