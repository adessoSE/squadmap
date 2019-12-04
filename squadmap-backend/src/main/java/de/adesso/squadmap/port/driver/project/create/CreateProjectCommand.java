package de.adesso.squadmap.port.driver.project.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectCommand {

    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private Boolean isExternal;
}
