package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.domain.WorkingOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
class EmployeeResponseMapper implements ResponseMapper<Employee, GetEmployeeResponse> {

    @Override
    public GetEmployeeResponse toResponse(Employee employee, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutEmployee> responseWithoutEmployees = new ArrayList<>();
        workingOns.forEach(workingOn -> {
            if (Objects.nonNull(workingOn.getEmployee()) && workingOn.getEmployee().getEmployeeId().equals(employee.getEmployeeId())) {
                responseWithoutEmployees.add(
                        new GetWorkingOnResponseWithoutEmployee(
                                workingOn.getWorkingOnId(),
                                new GetProjectResponseWithoutEmployee(
                                        workingOn.getProject().getProjectId(),
                                        workingOn.getProject().getTitle(),
                                        workingOn.getProject().getDescription(),
                                        workingOn.getProject().getSince(),
                                        workingOn.getProject().getUntil(),
                                        workingOn.getProject().getIsExternal(),
                                        workingOn.getProject().getSites()),
                                workingOn.getSince(),
                                workingOn.getUntil(),
                                workingOn.getWorkload()));
            }
        });
        return new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                employee.getImage(),
                responseWithoutEmployees);
    }
}
