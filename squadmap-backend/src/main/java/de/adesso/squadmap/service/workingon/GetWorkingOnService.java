package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class GetWorkingOnService implements GetWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;
    private final Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper;

    public GetWorkingOnService(WorkingOnRepository workingOnRepository, Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper) {
        this.workingOnRepository = workingOnRepository;
        this.workingOnMapper = workingOnMapper;
    }

    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        WorkingOn workingOn = workingOnRepository.findById(workingOnId)
                .orElseThrow(WorkingOnNotFoundException::new);
        return workingOnMapper.map(workingOn);
    }
}
