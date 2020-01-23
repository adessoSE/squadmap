package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetWorkingOnService implements GetWorkingOnUseCase {

    private final GetWorkingOnPort getWorkingOnPort;
    private final ListWorkingOnPort listWorkingOnPort;

    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        return GetWorkingOnResponse.getInstance(
                getWorkingOnPort.getWorkingOn(workingOnId),
                listWorkingOnPort.listWorkingOn());
    }
}
