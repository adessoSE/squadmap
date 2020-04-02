package de.adesso.squadmap.adapter.web.webentities.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
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
@Builder(builderClassName = "GetEmployeeResponseBuilder")
@JsonDeserialize(builder = GetEmployeeResponse.GetEmployeeResponseBuilder.class)
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetEmployeeResponseBuilder {

        public GetEmployeeResponseBuilder projects(List<GetWorkingOnResponseWithoutEmployee> projects) {
            this.projects = Optional.ofNullable(projects).stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            return this;
        }
    }
}
