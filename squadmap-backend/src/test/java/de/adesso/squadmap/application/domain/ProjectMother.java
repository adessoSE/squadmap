package de.adesso.squadmap.application.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectMother {

    public static Project.ProjectBuilder complete() {
        return Project.builder()
                .projectId(1L)
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>());
    }
}
