package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

@Service
public class GetWorkingOnService implements GetWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public GetWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        WorkingOn workingOn = workingOnRepository.findById(workingOnId).orElse(null);
        return new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                workingOn.getEmployee(),
                workingOn.getProject(),
                workingOn.getSince(),
                workingOn.getUntil());
    }
}
