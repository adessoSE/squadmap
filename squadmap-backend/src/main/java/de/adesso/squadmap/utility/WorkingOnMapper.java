package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;

public class WorkingOnMapper {

    public static GetWorkingOnResponse mapWorkingOnToWorkingOnResponse(WorkingOn workingOn) {
        GetEmployeeResponse employee = EmployeeMapper.mapEmployeeToEmployeeResponse(workingOn.getEmployee());
        GetProjectResponse project = ProjectMapper.mapProjectToGetProjectResponse(workingOn.getProject());
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employee,
                project,
                workingOn.getSince(),
                workingOn.getUntil());
    }
}
