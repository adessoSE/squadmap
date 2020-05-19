package de.adesso.squadmap.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder
public class Project {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;

    public Project(Long projectId, String title, String description, LocalDate since, LocalDate until,
                   Boolean isExternal, List<String> sites) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.since = since;
        this.until = until;
        this.isExternal = isExternal;
        this.sites = Optional.ofNullable(sites).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<String> getSites() {
        return new ArrayList<>(sites);
    }
}

