package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.domain.WorkingOn;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
class GetWorkingOnResponseWithoutEmployee {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetProjectResponseWithoutEmployee project;

    static GetWorkingOnResponseWithoutEmployee of(WorkingOn workingOn) {
        return GetWorkingOnResponseWithoutEmployee.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .project(GetProjectResponseWithoutEmployee.of(workingOn.getProject()))
                .build();
    }

    static List<GetWorkingOnResponseWithoutEmployee> of(List<WorkingOn> workingOns) {
        return workingOns.stream().map(GetWorkingOnResponseWithoutEmployee::of).collect(Collectors.toList());
    }
}

