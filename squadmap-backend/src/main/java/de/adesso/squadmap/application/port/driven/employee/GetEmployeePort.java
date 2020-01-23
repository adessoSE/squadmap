package de.adesso.squadmap.application.port.driven.employee;

import de.adesso.squadmap.application.domain.Employee;

public interface GetEmployeePort {

    Employee getEmployee(Long employeeId);
}
