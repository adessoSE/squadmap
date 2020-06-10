package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.WorkingOn;

import java.util.List;

public interface EmployeeResponseMapper {

    GetEmployeeResponse mapToResponseEntity(Employee i, List<WorkingOn> workingOns);
}
