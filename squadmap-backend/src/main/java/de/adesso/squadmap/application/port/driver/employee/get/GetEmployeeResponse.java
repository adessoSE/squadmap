package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "GetEmployeeResponseBuilder")
@JsonDeserialize(builder = GetEmployeeResponse.GetEmployeeResponseBuilder.class)
public class GetEmployeeResponse {

    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final Boolean isExternal;
    private final String image;
    private final List<GetWorkingOnResponseWithoutEmployee> projects;

    public static GetEmployeeResponse getInstance(Employee employee, List<WorkingOn> workingOns) {
        List<GetWorkingOnResponseWithoutEmployee> workingOnResponseWithoutEmployees = new ArrayList<>();
        workingOns.forEach(workingOn -> {
            if (Objects.nonNull(workingOn.getEmployee()) && workingOn.getEmployee().getEmployeeId().equals(employee.getEmployeeId())){
                workingOnResponseWithoutEmployees.add(GetWorkingOnResponseWithoutEmployee.getInstance(workingOn));
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
                workingOnResponseWithoutEmployees);
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class GetEmployeeResponseBuilder { }
}
