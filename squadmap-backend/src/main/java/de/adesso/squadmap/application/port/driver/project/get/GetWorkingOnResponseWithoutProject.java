package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderClassName = "GetWorkingOnResponseWithoutProjectBuilder")
@JsonDeserialize(builder = GetWorkingOnResponseWithoutProject.GetWorkingOnResponseWithoutProjectBuilder.class)
class GetWorkingOnResponseWithoutProject {

    private final Long workingOnId;
    private final GetEmployeeResponseWithoutProject employee;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutProjectBuilder {
    }
}
