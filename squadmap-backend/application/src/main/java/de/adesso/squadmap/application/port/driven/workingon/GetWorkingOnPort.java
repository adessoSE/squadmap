package de.adesso.squadmap.application.port.driven.workingon;

import de.adesso.squadmap.domain.WorkingOn;

public interface GetWorkingOnPort {

    WorkingOn getWorkingOn(Long workingOnId);
}
