package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class EmployeeResponseMapper implements ResponseMapper<Employee, GetEmployeeResponse> {

    @Override
    public GetEmployeeResponse toResponse(Employee employee, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutEmployee> workingOnResponseList = workingOns.stream()
                .filter(Objects::nonNull)
                .filter(workingOn -> workingOn.getEmployee().getEmployeeId().equals(employee.getEmployeeId()))
                .map(workingOn ->
                        GetWorkingOnResponseWithoutEmployee.builder()
                                .workingOnId(workingOn.getWorkingOnId())
                                .since(workingOn.getSince())
                                .until(workingOn.getUntil())
                                .workload(workingOn.getWorkload())
                                .project(GetProjectResponseWithoutEmployee.builder()
                                        .projectId(workingOn.getProject().getProjectId())
                                        .title(workingOn.getProject().getTitle())
                                        .description(workingOn.getProject().getDescription())
                                        .since(workingOn.getProject().getSince())
                                        .until(workingOn.getProject().getUntil())
                                        .sites(workingOn.getProject().getSites())
                                        .isExternal(workingOn.getProject().getIsExternal())
                                        .build())
                                .build())
                .collect(Collectors.toList());

        return GetEmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthday(employee.getBirthday())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .image(employee.getImage())
                .isExternal(employee.getIsExternal())
                .projects(workingOnResponseList)
                .build();
    }
}
