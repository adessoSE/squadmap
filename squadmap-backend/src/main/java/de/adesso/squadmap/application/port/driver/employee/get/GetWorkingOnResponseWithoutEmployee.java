package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderClassName = "GetWorkingOnResponseWithoutEmployeeBuilder")
@JsonDeserialize(builder = GetWorkingOnResponseWithoutEmployee.GetWorkingOnResponseWithoutEmployeeBuilder.class)
class GetWorkingOnResponseWithoutEmployee {

    private final Long workingOnId;
    private final GetProjectResponseWithoutEmployee project;
    private final LocalDate since;
    private final LocalDate until;
    private final int workload;

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutEmployeeBuilder { }
}

