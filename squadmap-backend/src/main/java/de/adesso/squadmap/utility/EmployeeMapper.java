package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static GetEmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {
        List<GetProjectResponse> projects = new ArrayList<>();
        employee.getProjects().forEach(workingOn ->
                new GetProjectResponse(
                        workingOn.getProject().getProjectId(),
                        workingOn.getProject().getTitle(),
                        workingOn.getProject().getDescription(),
                        workingOn.getProject().getSince(),
                        workingOn.getProject().getUntil(),
                        workingOn.getProject().getIsExternal(),
                        new ArrayList<>()));
        return new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                projects);
    }
}
