package de.adesso.squadmap.service.workingon;

import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListWorkingOnService implements ListWorkingOnUseCase {

    private final WorkingOnRepository workingOnRepository;
    private final Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper;

    public ListWorkingOnService(WorkingOnRepository workingOnRepository, Mapper<WorkingOn, GetWorkingOnResponse> workingOnMapper) {
        this.workingOnRepository = workingOnRepository;
        this.workingOnMapper = workingOnMapper;
    }

    @Override
    public List<GetWorkingOnResponse> listWorkingOn() {
        List<GetWorkingOnResponse> responses = new ArrayList<>();
        workingOnRepository.findAll().forEach(workingOn ->
                responses.add(workingOnMapper.map(workingOn)));
        return responses;
    }
}
