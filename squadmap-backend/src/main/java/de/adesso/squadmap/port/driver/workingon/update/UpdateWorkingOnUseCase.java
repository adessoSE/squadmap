package de.adesso.squadmap.port.driver.workingon.update;

public interface UpdateWorkingOnUseCase {

    void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId);
}
