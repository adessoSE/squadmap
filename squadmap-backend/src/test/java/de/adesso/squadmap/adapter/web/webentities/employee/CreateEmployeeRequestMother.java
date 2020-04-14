package de.adesso.squadmap.adapter.web.webentities.employee;

import java.time.LocalDate;

public class CreateEmployeeRequestMother {

    public static CreateEmployeeRequest.CreateEmployeeRequestBuilder complete(){
        return CreateEmployeeRequest.builder()
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file");
    }

}
