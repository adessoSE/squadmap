package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.port.driver.project.get.GetEmployeeResponseWithoutProject;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetWorkingOnResponseWithoutProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class GetProjectResponseMother {

    public static GetProjectResponse.GetProjectResponseBuilder complete() {
        return GetProjectResponse.builder()
                .projectId(1L)
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>())
                .employees(
                        Collections.singletonList(GetWorkingOnResponseWithoutProject.builder()
                                .workingOnId(1L)
                                .since(LocalDate.now())
                                .until(LocalDate.now())
                                .workload(0)
                                .employee(
                                        GetEmployeeResponseWithoutProject.builder()
                                                .employeeId(1L)
                                                .firstName("max")
                                                .lastName("mustermann")
                                                .birthday(LocalDate.now().minusYears(1))
                                                .email("max.mustermann@mustermail.com")
                                                .phone("0123456789")
                                                .isExternal(true)
                                                .image("file://file.file")
                                                .build())
                                .build()
                        )
                );
    }
}
