package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.WorkingOn;

public interface GetWorkingOnUseCase {

    WorkingOn getWorkingOn(Long workingOnId);
}
