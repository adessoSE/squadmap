package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.get.GetEmployeeResponseWithoutProject;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetWorkingOnResponseWithoutProject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapper {

    public GetProjectResponse mapProjectToGetProjectResponse(Project project) {
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
                                workingOn.getEmployee().getIsExternal()),
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

    public Project mapCreateProjectCommandToProject(CreateProjectCommand command) {
        return new Project(
                command.getTitle(),
                command.getDescription(),
                command.getSince(),
                command.getUntil(),
                command.getIsExternal());
    }
}
