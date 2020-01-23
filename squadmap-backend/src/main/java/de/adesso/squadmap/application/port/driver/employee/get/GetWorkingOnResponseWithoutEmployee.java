package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.WorkingOn;
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

    static GetWorkingOnResponseWithoutEmployee getInstance(WorkingOn workingOn) {
        return new GetWorkingOnResponseWithoutEmployee(
                workingOn.getWorkingOnId(),
                GetProjectResponseWithoutEmployee.getInstance(workingOn.getProject()),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutEmployeeBuilder { }
}

