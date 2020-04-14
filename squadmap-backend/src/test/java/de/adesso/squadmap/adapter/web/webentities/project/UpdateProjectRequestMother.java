package de.adesso.squadmap.adapter.web.webentities.project;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateProjectRequestMother {

    public static UpdateProjectRequest.UpdateProjectRequestBuilder complete() {
        return UpdateProjectRequest.builder()
                .title("squadmap")
                .description("test")
                .since(LocalDate.now())
                .until(LocalDate.now())
                .isExternal(true)
                .sites(new ArrayList<>());
    }
}
