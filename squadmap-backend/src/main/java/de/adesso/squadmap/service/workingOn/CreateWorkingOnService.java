package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkingOnService implements CreateWorkingOnUseCase {


    @Override
    public Long createWorkingOn(CreateWorkingOnCommand command) {
        return null;
    }
}
