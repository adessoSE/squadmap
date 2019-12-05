package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

    public static GetProjectResponse mapProjectToGetProjectResponse(Project project) {
        List<GetEmployeeResponse> employees = new ArrayList<>();
        project.getEmployees().forEach(workingOn ->
                employees.add(new GetEmployeeResponse(
                        workingOn.getEmployee().getEmployeeId(),
                        workingOn.getEmployee().getFirstName(),
                        workingOn.getEmployee().getLastName(),
                        workingOn.getEmployee().getBirthday(),
                        workingOn.getEmployee().getEmail(),
                        workingOn.getEmployee().getPhone(),
                        workingOn.getEmployee().getIsExternal(),
                        new ArrayList<>())));
        return new GetProjectResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getSince(),
                project.getUntil(),
                project.getIsExternal(),
                employees);
    }
}
