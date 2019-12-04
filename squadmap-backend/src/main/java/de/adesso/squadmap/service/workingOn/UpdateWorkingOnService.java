package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkingOnService implements UpdateWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public UpdateWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {
        WorkingOn workingOn = workingOnRepository.findById(workingOnId).orElse(null);
        workingOn.setEmployee(command.getEmployee());
        workingOn.setProject(command.getProject());
        workingOn.setSince(command.getSince());
        workingOn.setUntil(command.getUntil());
    }
}
