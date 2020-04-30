package de.adesso.squadmap.adapter.web.webentities.project;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateProjectRequestMother {

    public static CreateProjectRequest.CreateProjectRequestBuilder complete() {
        return CreateProjectRequest.builder()
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>());
    }

    public static CreateProjectRequest.CreateProjectRequestBuilder invalid() {
        return CreateProjectRequest.builder()
                .title(null)
                .description(null)
                .since(null)
                .until(null)
                .isExternal(null)
                .sites(null);
    }
}
