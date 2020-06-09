package de.adesso.squadmap.application.port.driven.workingon;

import de.adesso.squadmap.domain.WorkingOn;

import java.util.List;

public interface ListWorkingOnPort {

    List<WorkingOn> listWorkingOn();

    List<WorkingOn> listWorkingOnByEmployeeId(long employeeId);

    List<WorkingOn> listWorkingOnByProjectId(long projectId);
}
