package de.adesso.squadmap.adapter.web.webentities.workingon;

import de.adesso.squadmap.adapter.web.webentities.employee.GetEmployeeResponseMother;
import de.adesso.squadmap.adapter.web.webentities.project.GetProjectResponseMother;

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
