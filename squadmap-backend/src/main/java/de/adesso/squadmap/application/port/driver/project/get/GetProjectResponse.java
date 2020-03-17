package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public GetProjectResponse(Long projectId, String title, String description, LocalDate since, LocalDate until, Boolean isExternal, List<String> sites, List<GetWorkingOnResponseWithoutProject> employees) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = sites;
        this.employees = Optional.ofNullable(employees).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<GetWorkingOnResponseWithoutProject> getEmployees() {
        return new ArrayList<>(this.employees);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProjectResponseBuilder {
    }
}
