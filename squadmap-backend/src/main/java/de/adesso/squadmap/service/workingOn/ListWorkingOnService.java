package de.adesso.squadmap.service.workingOn;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.get.ListWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.utility.WorkingOnMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListWorkingOnService implements ListWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;

    public ListWorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    @Override
    public List<GetWorkingOnResponse> listWorkingOn() {
        List<GetWorkingOnResponse> responses = new ArrayList<>();
        workingOnRepository.findAll().forEach(workingOn ->
                responses.add(WorkingOnMapper.mapWorkingOnToWorkingOnResponse(workingOn)));
        return responses;
    }
}
