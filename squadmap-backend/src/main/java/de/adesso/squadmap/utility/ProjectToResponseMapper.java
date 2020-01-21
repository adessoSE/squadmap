package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.get.GetEmployeeResponseWithoutProject;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetWorkingOnResponseWithoutProject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectToResponseMapper implements Mapper<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse map(Project project) {
        List<GetWorkingOnResponseWithoutProject> workingOnResponses = new ArrayList<>();
        project.getEmployees().forEach(workingOn ->
                workingOnResponses.add(new GetWorkingOnResponseWithoutProject(
                        workingOn.getWorkingOnId(),
                        new GetEmployeeResponseWithoutProject(
                                workingOn.getEmployee().getEmployeeId(),
                                workingOn.getEmployee().getFirstName(),
                                workingOn.getEmployee().getLastName(),
                                workingOn.getEmployee().getBirthday(),
                                workingOn.getEmployee().getEmail(),
                                workingOn.getEmployee().getPhone(),
                                workingOn.getEmployee().getIsExternal(),
                                workingOn.getEmployee().getImage()),
                        workingOn.getSince(),
                        workingOn.getUntil(),
                        workingOn.getWorkload())));
        return new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites(),
                workingOnResponses);
    }
}
