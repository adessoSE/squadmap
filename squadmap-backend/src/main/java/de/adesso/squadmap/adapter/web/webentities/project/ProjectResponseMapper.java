package de.adesso.squadmap.adapter.web.webentities.project;

import de.adesso.squadmap.adapter.web.ResponseMapper;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class ProjectResponseMapper implements ResponseMapper<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse mapToResponseEntity(Project project, List<WorkingOn> workingOns) {
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
