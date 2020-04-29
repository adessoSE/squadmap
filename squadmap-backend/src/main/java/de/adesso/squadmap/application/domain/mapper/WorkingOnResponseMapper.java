package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;

import java.util.List;

public interface WorkingOnResponseMapper {

    GetWorkingOnResponse mapToResponseEntity(WorkingOn workingOn,
                                             List<WorkingOn> employeeWorkingOns,
                                             List<WorkingOn> projectWorkingOns);
}
