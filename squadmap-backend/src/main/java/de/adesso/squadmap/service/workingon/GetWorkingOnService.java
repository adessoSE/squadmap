package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.exceptions.workingon.WorkingOnNotFoundException;
import de.adesso.squadmap.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class GetWorkingOnService implements GetWorkingOnUseCase {

    private final GetWorkingOnPort getWorkingOnPort;
    private final Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper;

    public GetWorkingOnService(GetWorkingOnPort getWorkingOnPort, Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper) {
        this.getWorkingOnPort = getWorkingOnPort;
        this.workingOnMapper = workingOnMapper;
    }

    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        return workingOnMapper.map(getWorkingOnPort.getWorkingOn(workingOnId));
    }
}
