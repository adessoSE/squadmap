package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.WorkingOnResponseMapper;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ListWorkingOnService implements ListWorkingOnUseCase {

    private final ListWorkingOnPort listWorkingOnPort;
    private final WorkingOnResponseMapper workingOnResponseMapper;

    @Override
    @Transactional
    public List<GetWorkingOnResponse> listWorkingOn() {
        List<WorkingOn> workingOns = listWorkingOnPort.listWorkingOn();
        return workingOns.stream()
                .map(workingOn -> workingOnResponseMapper.mapToResponseEntity(
                        workingOn,
                        workingOns.stream()
                                .filter(tempWorkingOn -> tempWorkingOn.getEmployee().getEmployeeId()
                                        .equals(workingOn.getEmployee().getEmployeeId()))
                                .collect(Collectors.toList()),
                        workingOns.stream()
                                .filter(tempWorkingOn -> tempWorkingOn.getProject().getProjectId()
                                        .equals(workingOn.getProject().getProjectId()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
