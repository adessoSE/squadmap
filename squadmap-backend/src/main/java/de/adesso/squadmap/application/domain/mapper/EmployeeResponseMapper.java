package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;

import java.util.List;

public interface EmployeeResponseMapper {

    GetEmployeeResponse mapToResponseEntity(Employee i, List<WorkingOn> workingOns);
}
