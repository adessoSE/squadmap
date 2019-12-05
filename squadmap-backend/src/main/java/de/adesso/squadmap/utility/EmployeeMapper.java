package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static GetEmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {
        List<GetWorkingOnResponse> workingOnResponses = new ArrayList<>();
        employee.getProjects().forEach(workingOn ->
                workingOnResponses.add(new GetWorkingOnResponse(
                        workingOn.getWorkingOnId(),
                        null,
                        new GetProjectResponse(
                                workingOn.getProject().getProjectId(),
                                workingOn.getProject().getTitle(),
                                workingOn.getProject().getDescription(),
                                workingOn.getProject().getSince(),
                                workingOn.getProject().getUntil(),
                                workingOn.getProject().getIsExternal(),
                                null),
                        workingOn.getSince(),
                        workingOn.getUntil())));
        GetEmployeeResponse response = new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                workingOnResponses);
        return response;
    }
}
