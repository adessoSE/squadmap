package de.adesso.squadmap.port.driver.project.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

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
    @JsonProperty
    private boolean isExternal;
    @NotNull
    private List<@URL String> sites;
}
