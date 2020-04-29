package de.adesso.squadmap.application.service.workingon;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.RelationResponseMapper;
import de.adesso.squadmap.application.port.driven.workingon.GetWorkingOnPort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetWorkingOnService implements GetWorkingOnUseCase {

    private final GetWorkingOnPort getWorkingOnPort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final RelationResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;

    @Override
    @Transactional
    public GetWorkingOnResponse getWorkingOn(Long workingOnId) {
        WorkingOn workingOn = getWorkingOnPort.getWorkingOn(workingOnId);
        return workingOnResponseMapper.mapToResponseEntity(
                workingOn,
                listWorkingOnPort.listWorkingOnByEmployeeId(workingOn.getEmployee().getEmployeeId()),
                listWorkingOnPort.listWorkingOnByProjectId(workingOn.getProject().getProjectId()));
    }
}
