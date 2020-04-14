package de.adesso.squadmap.adapter.web.webentities.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@JsonDeserialize(builder = CreateProjectRequest.CreateProjectRequestBuilder.class)
public class CreateProjectRequest {

    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List< String> sites;

    public CreateProjectRequest(String title,
                                String description,
                                LocalDate since,
                                LocalDate until,
                                Boolean isExternal,
                                List<String> sites) {
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = Optional.ofNullable(sites).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public CreateProjectCommand asCommand() {
        return CreateProjectCommand.builder()
                .title(title)
                .description(description)
                .since(since)
                .until(until)
                .isExternal(isExternal)
                .sites(sites)
                .build();
    }

    public List<String> getSites() {
        return new ArrayList<>(sites);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CreateProjectRequestBuilder{}
}
