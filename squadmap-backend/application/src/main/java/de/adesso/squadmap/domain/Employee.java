package de.adesso.squadmap.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@RequiredArgsConstructor
public class Employee {

    Long employeeId;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    String phone;
    Boolean isExternal;
    String image;
}
