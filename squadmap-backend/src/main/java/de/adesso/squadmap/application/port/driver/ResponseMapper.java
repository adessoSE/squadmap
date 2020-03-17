package de.adesso.squadmap.application.port.driver;

import de.adesso.squadmap.application.domain.WorkingOn;

import java.util.List;

public interface ResponseMapper<I, O> {

    O toResponse(I i, List<WorkingOn> workingOns);
}
