package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkingOnService implements UpdateWorkingOnUseCase {


    @Override
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {

    }
}
