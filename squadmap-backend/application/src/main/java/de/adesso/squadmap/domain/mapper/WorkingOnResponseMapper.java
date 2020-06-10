package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.domain.WorkingOn;

import java.util.List;

public interface WorkingOnResponseMapper {

    GetWorkingOnResponse mapToResponseEntity(WorkingOn workingOn,
                                             List<WorkingOn> employeeWorkingOns,
                                             List<WorkingOn> projectWorkingOns);
}
