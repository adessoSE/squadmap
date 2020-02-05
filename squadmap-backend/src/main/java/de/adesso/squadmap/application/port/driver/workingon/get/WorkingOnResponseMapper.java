package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkingOnResponseMapper implements ResponseMapper<WorkingOn, GetWorkingOnResponse> {

    private final ResponseMapper<Employee, GetEmployeeResponse> employeeMapper;
    private final ResponseMapper<Project, GetProjectResponse> projectMapper;

    @Override
    public GetWorkingOnResponse toResponse(WorkingOn workingOn, List<WorkingOn> workingOns) {
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employeeMapper.toResponse(workingOn.getEmployee(), workingOns),
                projectMapper.toResponse(workingOn.getProject(), workingOns),
                workingOn.getSince(),
                workingOn.getUntil(),
                workingOn.getWorkload());
    }
}
