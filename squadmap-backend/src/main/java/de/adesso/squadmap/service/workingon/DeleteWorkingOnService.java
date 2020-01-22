package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.port.driven.workingon.DeleteWorkingOnPort;
import de.adesso.squadmap.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteWorkingOnService implements DeleteWorkingOnUseCase {

    private final DeleteWorkingOnPort deleteWorkingOnPort;

    public DeleteWorkingOnService(DeleteWorkingOnPort deleteWorkingOnPort) {
        this.deleteWorkingOnPort = deleteWorkingOnPort;
    }

    @Override
    public void deleteWorkingOn(Long workingOnId) {
        deleteWorkingOnPort.deleteWorkingOn(workingOnId);
    }
}
