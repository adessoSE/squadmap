package de.adesso.squadmap.adapter.web.webentities.workingon;

import de.adesso.squadmap.adapter.web.ResponseMapper;
import de.adesso.squadmap.adapter.web.webentities.employee.GetEmployeeResponse;
import de.adesso.squadmap.adapter.web.webentities.project.GetProjectResponse;
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
