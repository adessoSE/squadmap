package de.adesso.squadmap.application.port.driver.workingon.update;

public interface UpdateWorkingOnUseCase {

    void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId);
}
