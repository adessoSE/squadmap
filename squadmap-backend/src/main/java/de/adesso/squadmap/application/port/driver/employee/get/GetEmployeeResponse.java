package de.adesso.squadmap.application.port.driver.employee.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import scala.collection.immutable.Stream;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder(builderClassName = "GetEmployeeResponseBuilder")
@JsonDeserialize(builder = GetEmployeeResponse.GetEmployeeResponseBuilder.class)
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

    public GetEmployeeResponse(Long employeeId, String firstName, String lastName, LocalDate birthday, String email, String phone, Boolean isExternal, String image, List<GetWorkingOnResponseWithoutEmployee> projects) {
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetEmployeeResponseBuilder {
    }
}
