package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.WorkingOn;
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

    static GetWorkingOnResponseWithoutProject getInstance(WorkingOn workingOn) {
        return new GetWorkingOnResponseWithoutProject(
                workingOn.getWorkingOnId(),
                GetEmployeeResponseWithoutProject.getInstance(workingOn.getEmployee()),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetWorkingOnResponseWithoutProjectBuilder {
    }
}
