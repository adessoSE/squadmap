package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "GetWorkingOnResponseWithoutProjectBuilder")
@JsonDeserialize(builder = GetWorkingOnResponseWithoutProject.GetWorkingOnResponseWithoutProjectBuilder.class)
class GetWorkingOnResponseWithoutProject {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetEmployeeResponseWithoutProject employee;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutProjectBuilder {
    }
}
