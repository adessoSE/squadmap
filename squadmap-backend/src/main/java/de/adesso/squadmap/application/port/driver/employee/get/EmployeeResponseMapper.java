package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.ResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class EmployeeResponseMapper implements ResponseMapper<Employee, GetEmployeeResponse> {

    @Override
    public GetEmployeeResponse toResponse(Employee employee, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutEmployee> workingOnResponseList = workingOns.stream()
                .filter(Objects::nonNull)
                .filter(workingOn -> workingOn.getEmployee().getEmployeeId().equals(employee.getEmployeeId()))
                .map(workingOn -> new GetWorkingOnResponseWithoutEmployee(
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
                        workingOn.getWorkload()))
                .collect(Collectors.toList());

        return new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                employee.getImage(),
                workingOnResponseList);
    }
}
