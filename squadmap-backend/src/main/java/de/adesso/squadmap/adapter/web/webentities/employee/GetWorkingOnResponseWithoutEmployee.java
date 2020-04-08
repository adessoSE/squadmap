package de.adesso.squadmap.adapter.web.webentities.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "GetWorkingOnResponseWithoutEmployeeBuilder")
@JsonDeserialize(builder = GetWorkingOnResponseWithoutEmployee.GetWorkingOnResponseWithoutEmployeeBuilder.class)
class GetWorkingOnResponseWithoutEmployee {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetProjectResponseWithoutEmployee project;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutEmployeeBuilder {
    }
}

