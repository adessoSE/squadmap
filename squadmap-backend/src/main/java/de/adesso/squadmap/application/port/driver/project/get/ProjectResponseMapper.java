package de.adesso.squadmap.application.port.driver.project.get;

import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ProjectResponseMapper implements EntityResponseMapper<Project, GetProjectResponse> {

    @Override
    public GetProjectResponse mapToResponseEntity(Project project, List<WorkingOn> workingOns) {
        return GetProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .since(project.getSince())
                .until(project.getUntil())
                .sites(project.getSites())
                .isExternal(project.getIsExternal())
                .employees(workingOns.stream().map(workingOn ->
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
                        .collect(Collectors.toList()))
                .build();

    }
}
