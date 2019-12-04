package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.port.driver.workingOn.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteWorkingOnService implements DeleteWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public DeleteWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public void deleteWorkingOn(Long workingOnId) {
        workingOnRepository.deleteById(workingOnId);
    }
}
