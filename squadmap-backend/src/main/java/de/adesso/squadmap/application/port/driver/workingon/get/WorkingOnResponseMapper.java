package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.RelationResponseMapper;
import de.adesso.squadmap.application.domain.mapper.EntityResponseMapper;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class WorkingOnResponseMapper implements RelationResponseMapper<WorkingOn, GetWorkingOnResponse> {

    private final EntityResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    private final EntityResponseMapper<Project, GetProjectResponse> projectResponseMapper;

    public GetWorkingOnResponse mapToResponseEntity(WorkingOn workingOn,
                                                    List<WorkingOn> employeeWorkingOns,
                                                    List<WorkingOn> projectWorkingOns) {
        return GetWorkingOnResponse.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .employee(employeeResponseMapper.mapToResponseEntity(workingOn.getEmployee(), employeeWorkingOns))
                .project(projectResponseMapper.mapToResponseEntity(workingOn.getProject(), projectWorkingOns))
                .build();
    }
}
