package de.adesso.squadmap.application.domain;

import java.time.LocalDate;

public class EmployeeMother {

    public static Employee.EmployeeBuilder complete() {
        return Employee.builder()
                .employeeId(1L)
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file");
    }
}
