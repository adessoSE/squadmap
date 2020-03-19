package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import org.springframework.stereotype.Component;

@Component
class EmployeeDomainMapperImplementation implements EmployeeDomainMapper {

    public Employee mapToDomainEntity(CreateEmployeeCommand command) {
        return new Employee(
                null,
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.getIsExternal(),
                command.getImage());
    }

    public Employee mapToDomainEntity(UpdateEmployeeCommand command, long employeeId) {
        return new Employee(
                employeeId,
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.getIsExternal(),
                command.getImage());
    }
}
