package de.adesso.squadmap.application.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@RequiredArgsConstructor
public class Project {

    Long projectId;
    String title;
    String description;
    LocalDate since;
    LocalDate until;
    Boolean isExternal;
    List<String> sites;
}
