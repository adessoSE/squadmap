package de.adesso.squadmap.adapter.persistence;

import java.time.LocalDate;
import java.util.ArrayList;

class ProjectNeo4JEntityMother {

    static ProjectNeo4JEntity.ProjectNeo4JEntityBuilder complete() {
        return ProjectNeo4JEntity.builder()
                .projectId(1L)
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>())
                .employees(new ArrayList<>());
    }
}
