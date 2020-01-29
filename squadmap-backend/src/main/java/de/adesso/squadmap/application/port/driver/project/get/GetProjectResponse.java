package de.adesso.squadmap.application.port.driver.project.get;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class GetProjectResponse {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;
    List<GetWorkingOnResponseWithoutProject> employees;
}
