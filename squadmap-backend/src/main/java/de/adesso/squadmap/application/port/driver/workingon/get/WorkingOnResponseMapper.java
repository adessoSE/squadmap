package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.mapper.ResponseMapper;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class WorkingOnResponseMapper implements ResponseMapper<WorkingOn, GetWorkingOnResponse> {

    private final ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    private final ResponseMapper<Project, GetProjectResponse> projectResponseMapper;

    @Override
    public GetWorkingOnResponse mapToResponseEntity(WorkingOn workingOn, List<WorkingOn> workingOns) {
        return GetWorkingOnResponse.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .employee(employeeResponseMapper.mapToResponseEntity(workingOn.getEmployee(), workingOns))
                .project(projectResponseMapper.mapToResponseEntity(workingOn.getProject(), workingOns))
                .build();
    }
}
