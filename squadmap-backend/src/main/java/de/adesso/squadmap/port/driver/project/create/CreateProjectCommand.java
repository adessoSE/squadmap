package de.adesso.squadmap.port.driver.project.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectCommand {

    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;
    @NotNull
    @Size(max = 1000)
    private String description;
    @NotNull
    private LocalDate since;
    @NotNull
    private LocalDate until;
    private boolean isExternal;
}
