package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;

import java.time.LocalDate;

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
