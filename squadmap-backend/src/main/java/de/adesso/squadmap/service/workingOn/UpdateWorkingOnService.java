package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.utility.WorkingOnMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkingOnService implements UpdateWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public UpdateWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void updateWorkingOn(UpdateWorkingOnCommand command, Long workingOnId) {
        workingOnRepository.save(WorkingOnMapper.mapUpdateWorkingOnCommandToWorkingOn(command, workingOnId));
    }
}
