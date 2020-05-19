package de.adesso.squadmap.application.port.driver.workingon.get;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.EmployeeResponseMapper;
import de.adesso.squadmap.application.domain.mapper.ProjectResponseMapper;
import de.adesso.squadmap.application.domain.mapper.WorkingOnResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class WorkingOnResponseMapperImplementation implements WorkingOnResponseMapper {

    private final EmployeeResponseMapper employeeResponseMapper;
    private final ProjectResponseMapper projectResponseMapper;

    @Override
    public GetWorkingOnResponse mapToResponseEntity(WorkingOn workingOn,
                                                    List<WorkingOn> employeeWorkingOns,
                                                    List<WorkingOn> projectWorkingOns) {
        return GetWorkingOnResponse.builder()
                .workingOnId(workingOn.getWorkingOnId())
                .since(workingOn.getSince())
                .until(workingOn.getUntil())
                .workload(workingOn.getWorkload())
                .employee(employeeResponseMapper.mapToResponseEntity(workingOn.getEmployee(), employeeWorkingOns))
                .project(projectResponseMapper.mapToResponseEntity(workingOn.getProject(), projectWorkingOns))
                .build();
    }
}
