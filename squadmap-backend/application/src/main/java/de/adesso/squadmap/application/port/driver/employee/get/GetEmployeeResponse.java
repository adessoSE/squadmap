package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.WorkingOn;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static class GetEmployeeResponseBuilder {

        public GetEmployeeResponseBuilder projects(List<GetWorkingOnResponseWithoutEmployee> projects) {
            this.projects = Optional.ofNullable(projects).stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            return this;
        }
    }
}
