package de.adesso.squadmap.application.port.driver.project;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
public abstract class ProjectCommand {

    @NotEmpty
    @Size(min = 1, max = 100)
    private final String title;
    @NotNull
    @Size(max = 1000)
    private final String description;
    @NotNull
    private final LocalDate since;
    @NotNull
    private final LocalDate until;
    private final @NotNull Boolean isExternal;
    @NotNull
    private final List<@URL String> sites;

    public ProjectCommand(String title, String description, LocalDate since, LocalDate until, Boolean isExternal, List<String> sites) {
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = List.copyOf(sites);
    }

    public List<String> getSites() {
        return Collections.unmodifiableList(sites);
    }
}
