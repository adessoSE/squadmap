package de.adesso.squadmap.port.driver.workingOn.update;

public interface UpdateWorkingOnUseCase {

    void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId);
}
