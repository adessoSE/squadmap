package de.adesso.squadmap.application.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee {

    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;
    private final String phone;
    private final Boolean isExternal;
    private final String image;

    public static Employee withId(
            long employeeId,
            String firstName,
            String lastName,
            LocalDate birthday,
            String email,
            String phone,
            Boolean isExternal,
            String image) {
        return new Employee(
                employeeId,
                firstName,
                lastName,
                birthday,
                email,
                phone,
                isExternal,
                image);
    }

    public static Employee withoutId(
            String firstName,
            String lastName,
            LocalDate birthday,
            String email,
            String phone,
            Boolean isExternal,
            String image) {
        return new Employee(
                null,
                firstName,
                lastName,
                birthday,
                email,
                phone,
                isExternal,
                image);
    }
}
