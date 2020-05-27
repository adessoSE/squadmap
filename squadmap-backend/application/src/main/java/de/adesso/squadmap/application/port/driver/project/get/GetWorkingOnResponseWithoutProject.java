package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.domain.WorkingOn;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
class GetWorkingOnResponseWithoutProject {

    Long workingOnId;
    LocalDate since;
    LocalDate until;
    Integer workload;
    GetEmployeeResponseWithoutProject employee;

    static GetWorkingOnResponseWithoutProject of(WorkingOn workingOn) {
        return GetWorkingOnResponseWithoutProject.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .employee(GetEmployeeResponseWithoutProject.of(workingOn.getEmployee()))
                .build();
    }

    static List<GetWorkingOnResponseWithoutProject> of(List<WorkingOn> workingOns) {
        return workingOns.stream().map(GetWorkingOnResponseWithoutProject::of).collect(Collectors.toList());
    }
}
