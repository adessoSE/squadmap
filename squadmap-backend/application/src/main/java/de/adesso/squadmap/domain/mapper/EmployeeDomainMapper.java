package de.adesso.squadmap.domain.mapper;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.domain.Employee;

public interface EmployeeDomainMapper {

    Employee mapToDomainEntity(CreateEmployeeCommand command);

    Employee mapToDomainEntity(UpdateEmployeeCommand command, long employeeId);
}
