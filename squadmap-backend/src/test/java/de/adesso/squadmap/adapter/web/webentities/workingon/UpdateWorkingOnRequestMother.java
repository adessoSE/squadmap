package de.adesso.squadmap.adapter.web.webentities.workingon;

import java.time.LocalDate;

public class UpdateWorkingOnRequestMother {

    public static UpdateWorkingOnRequest.UpdateWorkingOnRequestBuilder complete() {
        return UpdateWorkingOnRequest.builder()
                .employeeId(1)
                .projectId(2)
                .since(LocalDate.now())
                .until(LocalDate.now())
                .workload(0);
    }
}
