package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = UpdateProjectRequest.UpdateProjectRequestBuilder.class)
public class UpdateProjectRequest {

    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List< String> sites;

    public UpdateProjectCommand asCommand() {
        return UpdateProjectCommand.builder()
                .title(title)
                .description(description)
                .since(since)
                .until(until)
                .isExternal(isExternal)
                .sites(sites)
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class UpdateProjectRequestBuilder{}
}
