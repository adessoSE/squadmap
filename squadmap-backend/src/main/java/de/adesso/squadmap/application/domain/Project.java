package de.adesso.squadmap.application.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {

    private final Long projectId;
    private final String title;
    private final String description;
    private final LocalDate since;
    private final LocalDate until;
    private final Boolean isExternal;
    private final List<String> sites;

    public static Project withId(
            long projectId,
            String title,
            String description,
            LocalDate since,
            LocalDate until,
            boolean isExternal,
            List<String> sites) {
        return new Project(
                projectId,
                title,
                description,
                since,
                until,
                isExternal,
                sites);
    }

    public static Project withoutId(
            String title,
            String description,
            LocalDate since,
            LocalDate until,
            boolean isExternal,
            List<String> sites) {
        return new Project(
                null,
                title,
                description,
                since,
                until,
                isExternal,
                sites);
    }
}
