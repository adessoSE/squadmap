package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetWorkingOnResponseMother {

    public static GetWorkingOnResponse.GetWorkingOnResponseBuilder complete() {
        return GetWorkingOnResponse.builder()
                .workingOnId(1L)
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0)
                .employee(GetEmployeeResponseMother.complete().build())
                .project(GetProjectResponseMother.complete().build());
    }
}
