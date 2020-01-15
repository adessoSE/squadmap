package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingon.delete.DeleteWorkingOnUseCase;
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
        if (!workingOnRepository.existsById(workingOnId)) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.deleteById(workingOnId);
    }
}
