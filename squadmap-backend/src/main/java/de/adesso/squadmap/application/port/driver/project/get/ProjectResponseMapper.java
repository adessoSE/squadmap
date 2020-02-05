package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.domain.WorkingOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ProjectResponseMapper implements ResponseMapper<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse toResponse(Project project, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutProject> getWorkingOnResponseWithoutProjects = new ArrayList<>();
        workingOns.forEach(workingOn -> {
            if (Objects.nonNull(workingOn.getProject()) && workingOn.getProject().getProjectId().equals(project.getProjectId())) {
                getWorkingOnResponseWithoutProjects.add(
                        new GetWorkingOnResponseWithoutProject(
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
                                workingOn.getWorkload()));
            }
        });
        return new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                project.getSites(),
                getWorkingOnResponseWithoutProjects);
    }
}
