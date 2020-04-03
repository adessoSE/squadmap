package de.adesso.squadmap.application.port.driver.project;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public abstract class ProjectCommand {

    @NotEmpty(message = "should not be empty")
    @Size(min = 1, max = 100, message = "has to be between {min} and {max} characters long")
    private final String title;
    @NotNull(message = "should not be null")
    @Size(max = 1000, message = "should not contain more than {max} characters")
    private final String description;
    @NotNull(message = "should not be null")
    private final LocalDate since;
    @NotNull(message = "should not be null")
    private final LocalDate until;
    @NotNull(message = "should not be null")
    private final Boolean isExternal;
    @NotNull(message = "should not be null")
    private final List<@URL(message = "has to be a valid url") String> sites;

    public ProjectCommand(String title, String description, LocalDate since, LocalDate until, Boolean isExternal, List<String> sites) {
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = Optional.ofNullable(sites).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<String> getSites() {
        return new ArrayList<>(this.sites);
    }
}
