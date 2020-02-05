package de.adesso.squadmap.application.port.driver.project.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.adesso.squadmap.application.port.driver.project.ProjectCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateProjectCommand extends ProjectCommand {

    @Builder
    @JsonCreator
    public UpdateProjectCommand(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("since") LocalDate since,
            @JsonProperty("until") LocalDate until,
            @JsonProperty("isExternal") Boolean isExternal,
            @JsonProperty("sites") List<String> sites) {
        super(title, description, since, until, isExternal, sites);
    }
}
