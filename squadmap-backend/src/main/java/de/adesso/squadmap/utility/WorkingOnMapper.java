package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkingOnMapper {

    private ProjectMapper projectMapper = new ProjectMapper();
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public GetWorkingOnResponse mapWorkingOnToWorkingOnResponse(WorkingOn workingOn) {
        GetEmployeeResponse employee = employeeMapper.mapEmployeeToEmployeeResponse(workingOn.getEmployee());
        GetProjectResponse project = projectMapper.mapProjectToGetProjectResponse(workingOn.getProject());
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employee,
                project,
                workingOn.getSince(),
                workingOn.getUntil());
    }
}
