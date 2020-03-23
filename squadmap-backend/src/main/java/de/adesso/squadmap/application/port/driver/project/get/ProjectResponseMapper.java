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
                .map(workingOn ->
                        GetWorkingOnResponseWithoutProject.builder()
                                .workingOnId(workingOn.getWorkingOnId())
                                .since(workingOn.getSince())
                                .until(workingOn.getUntil())
                                .workload(workingOn.getWorkload())
                                .employee(GetEmployeeResponseWithoutProject.builder()
                                                .employeeId(workingOn.getEmployee().getEmployeeId())
                                                .firstName(workingOn.getEmployee().getFirstName())
                                                .lastName(workingOn.getEmployee().getLastName())
                                                .birthday(workingOn.getEmployee().getBirthday())
                                                .email(workingOn.getEmployee().getEmail())
                                                .phone(workingOn.getEmployee().getPhone())
                                                .image(workingOn.getEmployee().getImage())
                                                .isExternal(workingOn.getEmployee().getIsExternal())
                                                .build())
                                .build())
                .collect(Collectors.toList());

        return GetProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .since(project.getSince())
                .until(project.getUntil())
                .sites(project.getSites())
                .isExternal(project.getIsExternal())
                .employees(workingOnResponseWithoutProjectList)
                .build();
    }
}
