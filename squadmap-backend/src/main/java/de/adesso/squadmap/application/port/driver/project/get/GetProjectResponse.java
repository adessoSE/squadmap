package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder(builderClassName = "GetProjectResponseBuilder")
@JsonDeserialize(builder = GetProjectResponse.GetProjectResponseBuilder.class)
public class GetProjectResponse {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;
    List<GetWorkingOnResponseWithoutProject> employees;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProjectResponseBuilder {
    }
}
