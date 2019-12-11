package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.utility.WorkingOnMapper;
import de.adesso.squadmap.exceptions.WorkingOnNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetWorkingOnService implements GetWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;
    private final WorkingOnMapper workingOnMapper;

    public GetWorkingOnService(WorkingOnRepository workingOnRepository, WorkingOnMapper workingOnMapper) {
        this.workingOnRepository = workingOnRepository;
        this.workingOnMapper = workingOnMapper;
    }

    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        if(!workingOnRepository.existsById(workingOnId)){
            throw new WorkingOnNotFoundException();
        }
        WorkingOn workingOn = workingOnRepository.findById(workingOnId).orElse(null);
        return workingOnMapper.mapWorkingOnToWorkingOnResponse(workingOn);
    }
}
