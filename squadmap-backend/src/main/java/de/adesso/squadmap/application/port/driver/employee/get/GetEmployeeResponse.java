package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder
public class GetEmployeeResponse {

    Long employeeId;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    String phone;
    Boolean isExternal;
    String image;
    List<GetWorkingOnResponseWithoutEmployee> projects;

    private GetEmployeeResponse(Long employeeId,
                                String firstName,
                                String lastName,
                                LocalDate birthday,
                                String email,
                                String phone,
                                Boolean isExternal,
                                String image,
                                List<GetWorkingOnResponseWithoutEmployee> projects) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.isExternal = isExternal;
        this.image = image;
        this.projects = Optional.ofNullable(projects).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<GetWorkingOnResponseWithoutEmployee> getProjects() {
        return new ArrayList<>(this.projects);
    }

    static GetEmployeeResponse of(Employee employee, List<WorkingOn> workingOns) {
        return GetEmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthday(employee.getBirthday())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .image(employee.getImage())
                .isExternal(employee.getIsExternal())
                .projects(GetWorkingOnResponseWithoutEmployee.of(workingOns))
                .build();
    }
}
