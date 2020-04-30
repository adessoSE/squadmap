package de.adesso.squadmap.application.domain.mapper.implementation;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import org.springframework.stereotype.Component;

@Component
class EmployeeDomainMapperImplementation implements EmployeeDomainMapper {

    @Override
    public Employee mapToDomainEntity(CreateEmployeeCommand command) {
        return Employee.builder()
                .employeeId(null)
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthday(command.getBirthday())
                .email(command.getEmail())
                .phone(command.getPhone())
                .image(command.getImage())
                .isExternal(command.getIsExternal())
                .build();
    }

    @Override
    public Employee mapToDomainEntity(UpdateEmployeeCommand command, long employeeId) {
        return Employee.builder()
                .employeeId(employeeId)
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthday(command.getBirthday())
                .email(command.getEmail())
                .phone(command.getPhone())
                .image(command.getImage())
                .isExternal(command.getIsExternal())
                .build();
    }
}
