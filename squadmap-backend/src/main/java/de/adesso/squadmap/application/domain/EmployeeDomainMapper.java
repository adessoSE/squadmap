package de.adesso.squadmap.application.domain;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDomainMapper {

    public Employee mapToDomainEntity(CreateEmployeeCommand command) {
        return Employee.withoutId(
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.isExternal(),
                command.getImage());
    }

    public Employee mapToDomainEntity(UpdateEmployeeCommand command, long employeeId) {
        return Employee.withId(
                employeeId,
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.isExternal(),
                command.getImage());
    }
}
