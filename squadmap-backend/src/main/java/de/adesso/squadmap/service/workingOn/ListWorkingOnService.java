package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.ListWorkingOnUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListWorkingOnService implements ListWorkingOnUseCase {


    @Override
    public List<GetWorkingOnResponse> listWorkingOn() {
        return null;
    }
}
