package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProjectResponseMapper implements ResponseMapper<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse toResponse(Project project, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutProject> workingOnResponseWithoutProjectList = workingOns.stream()
                .filter(Objects::nonNull)
                .filter(workingOn -> workingOn.getProject().getProjectId().equals(project.getProjectId()))
                .map(workingOn -> new GetWorkingOnResponseWithoutProject(
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
                        workingOn.getWorkload()))
                .collect(Collectors.toList());

        return new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites(),
                workingOnResponseWithoutProjectList);
    }
}
