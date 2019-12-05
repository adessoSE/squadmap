package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;

public class WorkingOnMapper {

    public static GetWorkingOnResponse mapWorkingOnToWorkingOnResponse(WorkingOn workingOn) {
        GetEmployeeResponse employee = EmployeeMapper.mapEmployeeToEmployeeResponse(workingOn.getEmployee());
        GetProjectResponse project = ProjectMapper.mapProjectToGetProjectResponse(workingOn.getProject());
        GetWorkingOnResponse response = new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employee,
                project,
                workingOn.getSince(),
                workingOn.getUntil());
        return response;
    }
}
