package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetWorkingOnService implements GetWorkingOnUseCase {


    @Override
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        return null;
    }
}
