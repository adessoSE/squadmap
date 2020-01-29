package de.adesso.squadmap.application.port.driver.project.get;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
class GetWorkingOnResponseWithoutProject {

    Long workingOnId;
    GetEmployeeResponseWithoutProject employee;
    LocalDate since;
    LocalDate until;
    Integer workload;
}
