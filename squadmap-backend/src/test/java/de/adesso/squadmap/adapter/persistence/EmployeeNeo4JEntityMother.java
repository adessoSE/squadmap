package de.adesso.squadmap.adapter.persistence;

import java.time.LocalDate;
import java.util.ArrayList;

class EmployeeNeo4JEntityMother {

    static EmployeeNeo4JEntity.EmployeeNeo4JEntityBuilder complete() {
        return EmployeeNeo4JEntity.builder()
                .employeeId(1L)
                .firstName("max")
                .lastName("mustermann")
                .birthday(LocalDate.now().minusYears(1))
                .email("max.mustermann@mustermail.com")
                .phone("0123456789")
                .isExternal(true)
                .image("file://file.file")
                .projects(new ArrayList<>());
    }
}
