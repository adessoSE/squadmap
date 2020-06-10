package de.adesso.squadmap.adapter.web.webentities.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateEmployeeRequestMother {

    public static UpdateEmployeeRequest.UpdateEmployeeRequestBuilder complete() {
        return UpdateEmployeeRequest.builder()
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file");
    }

    public static UpdateEmployeeRequest.UpdateEmployeeRequestBuilder invalid() {
        return UpdateEmployeeRequest.builder()
                .firstName(null)
                .lastName(null)
                .birthday(null)
                .email(null)
                .phone(null)
                .isExternal(null)
                .image(null);
    }
}
