package de.adesso.squadmap.application.port.driver.employee.create;

import java.time.LocalDate;

public class CreateEmployeeCommandMother {

    public static CreateEmployeeCommand.CreateEmployeeCommandBuilder complete() {
        return CreateEmployeeCommand.builder()
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file");
    }
}
