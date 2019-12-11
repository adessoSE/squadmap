package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetProjectResponseWithoutEmployee;
import de.adesso.squadmap.port.driver.employee.get.GetWorkingOnResponseWithoutEmployee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeToResponseMapper implements Mapper<Employee, GetEmployeeResponse> {

    @Override
    public GetEmployeeResponse map(Employee employee) {
        List<GetWorkingOnResponseWithoutEmployee> workingOnResponses = new ArrayList<>();
        employee.getProjects().forEach(workingOn ->
                workingOnResponses.add(new GetWorkingOnResponseWithoutEmployee(
                        workingOn.getWorkingOnId(),
                        new GetProjectResponseWithoutEmployee(
                                workingOn.getProject().getProjectId(),
                                workingOn.getProject().getTitle(),
                                workingOn.getProject().getDescription(),
                                workingOn.getProject().getSince(),
                                workingOn.getProject().getUntil(),
                                workingOn.getProject().getIsExternal()),
                        workingOn.getSince(),
                        workingOn.getUntil())));
        return new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                workingOnResponses);
    }
}
