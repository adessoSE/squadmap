package de.adesso.squadmap.application.domain;

import java.util.List;

public interface ResponseMapper<I, O> {

    O toResponse(I i, List<WorkingOn> workingOns);
}
