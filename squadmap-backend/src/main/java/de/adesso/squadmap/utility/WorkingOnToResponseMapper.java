package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkingOnToResponseMapper implements Mapper<WorkingOn, GetWorkingOnResponse> {

    private final EmployeeToResponseMapper employeeMapper;
    private final ProjectToResponseMapper projectMapper;

    public WorkingOnToResponseMapper(EmployeeToResponseMapper employeeMapper, ProjectToResponseMapper projectMapper) {
        this.employeeMapper = employeeMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public GetWorkingOnResponse map(WorkingOn workingOn) {
        GetEmployeeResponse employee = employeeMapper.map(workingOn.getEmployee());
        GetProjectResponse project = projectMapper.map(workingOn.getProject());
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employee,
                project,
                workingOn.getSince(),
                workingOn.getUntil());
    }
}
