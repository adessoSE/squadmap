package de.adesso.squadmap.application.port.driver.employee.update;

import java.time.LocalDate;

public class UpdateEmployeeCommandMother {

    public static UpdateEmployeeCommand.UpdateEmployeeCommandBuilder complete() {
        return UpdateEmployeeCommand.builder()
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file");
    }
}
