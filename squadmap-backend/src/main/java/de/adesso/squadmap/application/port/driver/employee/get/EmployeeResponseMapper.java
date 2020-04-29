package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class EmployeeResponseMapper implements EntityResponseMapper<Employee, GetEmployeeResponse> {

    @Override
    public GetEmployeeResponse mapToResponseEntity(Employee employee, List<WorkingOn> workingOns) {
        return GetEmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthday(employee.getBirthday())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .image(employee.getImage())
                .isExternal(employee.getIsExternal())
                .projects(workingOns.stream().map(workingOn ->
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
                        .collect(Collectors.toList()))
                .build();
    }
}
