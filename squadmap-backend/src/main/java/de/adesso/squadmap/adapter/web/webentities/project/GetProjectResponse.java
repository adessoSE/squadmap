package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetProjectResponseBuilder {

        public GetProjectResponseBuilder employees(List<GetWorkingOnResponseWithoutProject> employees) {
            this.employees = Optional.ofNullable(employees).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
            return this;
        }
    }
}
