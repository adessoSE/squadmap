package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ListWorkingOnService implements ListWorkingOnUseCase {

    private final ListWorkingOnPort listWorkingOnPort;

    @Override
    @Transactional
    public List<WorkingOn> listWorkingOn() {
        return listWorkingOnPort.listWorkingOn();
    }
}
