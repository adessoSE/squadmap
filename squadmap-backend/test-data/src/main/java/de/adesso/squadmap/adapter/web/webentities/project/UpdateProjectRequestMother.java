package de.adesso.squadmap.adapter.web.webentities.project;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static UpdateProjectRequest.UpdateProjectRequestBuilder invalid() {
        return UpdateProjectRequest.builder()
                .title(null)
                .description(null)
                .since(null)
                .until(null)
                .isExternal(null)
                .sites(null);
    }
}
