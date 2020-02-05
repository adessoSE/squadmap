package de.adesso.squadmap.application.port.driven.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;

public interface CreateWorkingOnPort {

    long createWorkingOn(WorkingOn workingOn);
}
