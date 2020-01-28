package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProjectResponseBuilder {
    }
}
