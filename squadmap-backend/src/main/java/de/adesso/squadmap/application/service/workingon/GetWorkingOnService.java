package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetWorkingOnService implements GetWorkingOnUseCase {

    private final GetWorkingOnPort getWorkingOnPort;

    @Override
    @Transactional
    public WorkingOn getWorkingOn(Long workingOnId) {
        return getWorkingOnPort.getWorkingOn(workingOnId);
    }
}
