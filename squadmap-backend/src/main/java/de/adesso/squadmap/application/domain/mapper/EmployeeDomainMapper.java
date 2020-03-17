package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;

public interface EmployeeDomainMapper {

    Employee mapToDomainEntity(CreateEmployeeCommand command);
    Employee mapToDomainEntity(UpdateEmployeeCommand command, long employeeId);
}
