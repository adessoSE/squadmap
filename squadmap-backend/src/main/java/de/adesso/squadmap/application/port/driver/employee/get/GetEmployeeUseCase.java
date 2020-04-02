package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;

public interface GetEmployeeUseCase {

    Employee getEmployee(Long employeeId);
}
