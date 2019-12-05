package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

    public static GetProjectResponse mapProjectToGetProjectResponse(Project project) {
        List<GetWorkingOnResponse> workingOnResponses = new ArrayList<>();
        project.getEmployees().forEach(workingOn ->
                workingOnResponses.add(new GetWorkingOnResponse(
                        workingOn.getWorkingOnId(),
                        new GetEmployeeResponse(
                                workingOn.getEmployee().getEmployeeId(),
                                workingOn.getEmployee().getFirstName(),
                                workingOn.getEmployee().getLastName(),
                                workingOn.getEmployee().getBirthday(),
                                workingOn.getEmployee().getEmail(),
                                workingOn.getEmployee().getPhone(),
                                workingOn.getEmployee().getIsExternal(),
                                null),
                        null,
                        workingOn.getSince(),
                        workingOn.getUntil())));
        GetProjectResponse response = new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                workingOnResponses);
        return response;
    }
}
