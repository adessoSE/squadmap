package de.adesso.squadmap.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (Objects.nonNull(sites)) {
            this.sites = Collections.unmodifiableList(List.copyOf(sites));
        } else {
            this.sites = null;
        }
    }
}
