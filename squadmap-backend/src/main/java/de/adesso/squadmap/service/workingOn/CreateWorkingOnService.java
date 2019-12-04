package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkingOnService implements CreateWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public CreateWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public Long createWorkingOn(CreateWorkingOnCommand command) {
        WorkingOn workingOn = new WorkingOn(
                command.getEmployee(),
                command.getProject(),
                command.getSince(),
                command.getUntil());
        return workingOnRepository.save(workingOn).getWorkingOnId();
    }
}
