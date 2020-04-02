package de.adesso.squadmap.adapter.web.webentities.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class GetEmployeeResponseMother {

    public static GetEmployeeResponse.GetEmployeeResponseBuilder complete() {
        return GetEmployeeResponse.builder()
                .employeeId(1L)
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file")
                .projects(
                        Collections.singletonList(
                                GetWorkingOnResponseWithoutEmployee.builder()
                                        .workingOnId(1L)
                                        .since(LocalDate.now())
                                        .until(LocalDate.now())
                                        .workload(0)
                                        .project(
                                                GetProjectResponseWithoutEmployee.builder()
                                                        .projectId(1L)
                                                        .title("squadmap")
                                                        .description("test")
                                                        .since(LocalDate.now())
                                                        .until(LocalDate.now())
                                                        .isExternal(true)
                                                        .sites(new ArrayList<>())
                                                        .build())
                                        .build()
                        )
                );
    }
}
