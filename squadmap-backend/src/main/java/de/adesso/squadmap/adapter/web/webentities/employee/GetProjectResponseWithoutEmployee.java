package de.adesso.squadmap.adapter.web.webentities.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder(builderClassName = "GetProjectResponseWithoutEmployeeBuilder")
@JsonDeserialize(builder = GetProjectResponseWithoutEmployee.GetProjectResponseWithoutEmployeeBuilder.class)
class GetProjectResponseWithoutEmployee {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetProjectResponseWithoutEmployeeBuilder {
    }
}
