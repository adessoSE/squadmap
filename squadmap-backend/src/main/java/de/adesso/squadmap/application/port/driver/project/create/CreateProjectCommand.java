package de.adesso.squadmap.application.port.driver.project.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder(builderClassName = "CreateProjectCommandBuilder")
@JsonDeserialize(builder = CreateProjectCommand.CreateProjectCommandBuilder.class)
public class CreateProjectCommand {

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
    @JsonProperty
    private final boolean isExternal;
    @NotNull
    private final List<@URL String> sites;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CreateProjectCommandBuilder { }
}

